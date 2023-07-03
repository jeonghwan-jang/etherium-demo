package com.blockchain.demo.service;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.service.BlockNumberHistoryService;
import com.blockchain.demo.domain.service.TransactionEventService;
import com.blockchain.demo.domain.service.WalletService;
import jakarta.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

@Slf4j
@RequiredArgsConstructor
@Service
public class Web3jService {

    private final BigInteger GAS_LIMIT = BigInteger.valueOf(21000);

    private final Web3j web3;

    private final BlockNumberHistoryService blockNumberHistoryService;

    private final WalletService walletService;

    private final TransactionEventService transactionEventService;

    @Nullable
    @Transactional(readOnly = true)
    public BigInteger getSavedLatestBlockNumber() {
        return this.blockNumberHistoryService.getLatestBlockNumber();
    }

    @Transactional
    public void parseBlock(Block block) throws IOException {
        BigInteger blockNumber = block.getNumber();

        this.blockNumberHistoryService.saveIfNotExists(blockNumber);

        this.transactionEventService.updateBlockConfirmation(blockNumber);

        List<TransactionResult> transactions = block.getTransactions();
        for (TransactionResult<?> transactionResult : transactions) {
            this.parseTransaction((Transaction) transactionResult.get());
        }
    }

    @Transactional
    public void parseTransaction(Transaction tx) throws IOException {
        BigInteger blockNumber = Objects.isNull(tx.getBlockNumberRaw()) ? null : tx.getBlockNumber();
        String hash = tx.getHash();
        String from = tx.getFrom();
        String to = tx.getTo();
        BigDecimal amount = Convert.fromWei(tx.getValue().toString(), Unit.ETHER);
        BigDecimal gasPrice = Convert.fromWei(tx.getGasPrice().toString(), Unit.ETHER);
        BigDecimal translationFee = null;

        boolean isValidFrom = this.walletService.existsByAddress(from);
        boolean isValidTo = false;
        if (!Objects.isNull(to)) {
            isValidTo = this.walletService.existsByAddress(to);
        }

        boolean isNotTrackingTarget = !isValidFrom && !isValidTo;
        if (isNotTrackingTarget) {
            return;
        }

        Optional<TransactionReceipt> transactionReceiptOptional = this.web3.ethGetTransactionReceipt(hash).send()
            .getTransactionReceipt();
        boolean isSuccess = false;
        TransactionEventStatus status = TransactionEventStatus.PENDING;
        Integer blockConfirmation = null;
        if (transactionReceiptOptional.isPresent()) {
            TransactionReceipt transactionReceipt = transactionReceiptOptional.get();
            isSuccess = transactionReceipt.isStatusOK(); // TODO: 실패에 따른 핸들링 필요
            status = TransactionEventStatus.MINED;
            blockConfirmation = 0;
            translationFee = Convert.fromWei(transactionReceipt.getGasUsed().toString(), Unit.ETHER).multiply(gasPrice);
        }

        this.transactionEventService.saveIfNotExists(blockNumber, from, to, hash, amount, gasPrice, translationFee,
                                                     blockConfirmation, status);
    }

    public String sendSimpleTransaction(Credentials credentials,
                                        String to,
                                        BigDecimal amount) throws Exception {
        String from = credentials.getAddress();

        BigInteger nonce = this.web3.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send()
            .getTransactionCount();
        BigInteger value = Convert.toWei(amount, Unit.ETHER).toBigInteger();
        BigInteger gasPrice = this.getGasPrice();

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, GAS_LIMIT, to, value);
        String signMessage = Numeric.toHexString(
            TransactionEncoder.signMessage(rawTransaction, credentials)
        );

        EthSendTransaction sendTransaction = this.web3.ethSendRawTransaction(signMessage).send();
        if (sendTransaction.hasError()) {
            throw new RuntimeException(sendTransaction.getError().getMessage());
        }
        return sendTransaction.getTransactionHash();
    }

    public BigInteger getGasPrice() {
        try {
            return this.web3.ethGasPrice().send().getGasPrice();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ErrorCodeException(ErrorCode.FAILED_GET_GAS_PRICE);
        }
    }
}
