package com.blockchain.demo.repository.mapper;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.TransactionEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-02T11:26:49+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.1.1.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
public class TransactionEventMapperImpl implements TransactionEventMapper {

    @Override
    public TransactionEvent of(BigInteger blockNumber, String fromAddress, String toAddress, String hash, BigDecimal amount, BigDecimal gasPrice, BigDecimal translationFee, int blockConfirmation, TransactionEventStatus status) {
        if ( blockNumber == null && fromAddress == null && toAddress == null && hash == null && amount == null && gasPrice == null && translationFee == null && status == null ) {
            return null;
        }

        BigInteger blockNumber1 = null;
        blockNumber1 = blockNumber;
        String fromAddress1 = null;
        fromAddress1 = fromAddress;
        String toAddress1 = null;
        toAddress1 = toAddress;
        String hash1 = null;
        hash1 = hash;
        BigDecimal amount1 = null;
        amount1 = amount;
        BigDecimal gasPrice1 = null;
        gasPrice1 = gasPrice;
        BigDecimal translationFee1 = null;
        translationFee1 = translationFee;
        int blockConfirmation1 = 0;
        blockConfirmation1 = blockConfirmation;
        TransactionEventStatus status1 = null;
        status1 = status;

        Long id = null;

        TransactionEvent transactionEvent = new TransactionEvent( id, blockNumber1, fromAddress1, toAddress1, hash1, amount1, gasPrice1, translationFee1, blockConfirmation1, status1 );

        return transactionEvent;
    }
}
