package com.blockchain.demo.service.subscriber;

import com.blockchain.demo.service.Web3jService;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlockSubscriber {

    private static BigInteger currentBlockNumber;

    private final Web3j web3;

    private final Web3jService web3jService;

    // TODO: 여유가 된다면 별도 실행 모듈로 분리
    @PostConstruct
    public void init() {
        new Thread(this::blockTracking).start();
    }

    private void blockTracking() {
        this.blockTracking(this.web3jService.getSavedLatestBlockNumber());
    }

    private void blockTracking(BigInteger startBlockNumber) {
        if (!Objects.isNull(startBlockNumber)) {
            this.web3.replayPastBlocksFlowable(
                    DefaultBlockParameter.valueOf(startBlockNumber), true)
                .subscribe(this::blockTrackingOnNext, this::blockTrackingOnError,
                           this::blockTrackingOnComplete);
        } else {
            this.web3.blockFlowable(true)
                .subscribe(this::blockTrackingOnNext, this::blockTrackingOnError,
                           this::blockTrackingOnComplete);
        }
    }

    private void blockTrackingOnNext(EthBlock ethBlock) throws IOException {
        Block block = ethBlock.getBlock();
        BigInteger blockNumber = block.getNumber();
        BigInteger diff = Objects.isNull(currentBlockNumber) ? BigInteger.ZERO
            : blockNumber.subtract(currentBlockNumber).subtract(BigInteger.ONE);
        if (0 < diff.compareTo(BigInteger.ZERO)) {
            while (BigInteger.ZERO.compareTo(diff) < 1) {
                BigInteger lostBlockNumber = blockNumber.subtract(diff);
                diff = diff.subtract(BigInteger.ONE);

                Block lostBlock = this.web3.ethGetBlockByNumber(DefaultBlockParameter.valueOf(lostBlockNumber), true)
                    .send().getBlock();
                this.web3jService.parseBlock(lostBlock);
            }
        }

        currentBlockNumber = blockNumber;
        this.web3jService.parseBlock(block);
    }

    private void blockTrackingOnError(Throwable throwable) {
        log.error("A problem occurred while tracking the block.", throwable);
        this.blockTracking();
    }

    private void blockTrackingOnComplete() {
        log.info("Block tracking is complete. Will be proceed again.");
        this.blockTracking(null);
    }
}
