package com.blockchain.demo.service.subscriber;

import com.blockchain.demo.service.Web3jService;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Async;

@Slf4j
@RequiredArgsConstructor
@Service
public class PendingTransactionSubscriber {

    private final Web3j web3;

    private final Web3jService web3jService;

    // TODO: 여유가 된다면 별도 실행 모듈로 분리
    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Async.defaultExecutorService();
        scheduledExecutorService.scheduleAtFixedRate(this::pendingTransactionTracking, 0, 3 * 1000,
                                                     TimeUnit.MILLISECONDS);
    }

    private void pendingTransactionTracking() {
        try {
            EthFilter filter = this.web3.ethNewPendingTransactionFilter().send();
            if (filter.hasError()) {
                this.pendingTransactionTracking();
                return;
            }

            EthLog ethLog = this.web3.ethGetFilterChanges(filter.getFilterId()).send();
            if (!ethLog.hasError()) {
                if (0 < ethLog.getLogs().size()) {
                    log.info("Received pending transaction count: {}", ethLog.getLogs().size());
                }
                for (EthLog.LogResult logResult : ethLog.getLogs()) {
                    if (logResult instanceof EthLog.Hash) {
                        String transactionHash = ((EthLog.Hash) logResult).get();
                        try {
                            Optional<Transaction> transactionOptional = this.web3.ethGetTransactionByHash(
                                    transactionHash)
                                .send().getTransaction();
                            if (transactionOptional.isPresent()) {
                                this.web3jService.parseTransaction(transactionOptional.get());
                            }
                        } catch (IOException e) {
                            log.error("Failed get transaction by hash", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
