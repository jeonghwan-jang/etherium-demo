package com.blockchain.demo.domain.service;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.TransactionEvent;
import com.blockchain.demo.dto.TransactionEventDto;
import com.blockchain.demo.dto.request.PagingRequestDto;
import com.blockchain.demo.repository.TransactionEventRepository;
import com.blockchain.demo.repository.mapper.TransactionEventMapper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
@Service
public class TransactionEventService {

    private static final int TARGET_BLOCK_CONFIRMATION = 12;

    private final TransactionEventRepository transactionEventRepository;

    public void saveIfNotExists(final BigInteger blockNumber,
                                final String from,
                                final String to,
                                final String hash,
                                final BigDecimal amount,
                                final BigDecimal gasPrice,
                                final BigDecimal translationFee,
                                final Integer blockConfirmation,
                                final TransactionEventStatus status) {
        boolean isNotDuplicated = !this.existsByHashAndStatus(hash, status);
        if (isNotDuplicated) {
            this.transactionEventRepository.save(
                TransactionEventMapper.INSTANCE.of(blockNumber, from, to, hash, amount, gasPrice, translationFee,
                                                   blockConfirmation, status)
            );
        }
    }

    private boolean existsByHashAndStatus(String hash,
                                          TransactionEventStatus status) {
        return this.transactionEventRepository.existsByHashAndStatus(hash, status);
    }

    public void updateBlockConfirmation(final BigInteger blockNumber) {
        List<TransactionEvent> transactionEvents = this.transactionEventRepository.getOnlyMinedStatus();
        transactionEvents.forEach(transactionEvent -> {
            int blockConfirmation = blockNumber.subtract(transactionEvent.getBlockNumber()).intValue();
            if (TARGET_BLOCK_CONFIRMATION <= blockConfirmation) {
                this.saveIfNotExists(transactionEvent.getBlockNumber(),
                                     transactionEvent.getFromAddress(),
                                     transactionEvent.getToAddress(),
                                     transactionEvent.getHash(),
                                     transactionEvent.getAmount(),
                                     transactionEvent.getGasPrice(),
                                     transactionEvent.getTranslationFee(),
                                     blockConfirmation,
                                     TransactionEventStatus.CONFIRMED);
            } else {
                transactionEvent.changeBlockConfirmation(blockConfirmation);
            }
        });
    }

    public List<TransactionEventDto> getTransactionEvents(String address,
                                                          PagingRequestDto requestDto) {
        return this.transactionEventRepository.getTransactionEvents(address, requestDto)
            .map(TransactionEventMapper.INSTANCE::toDto)
            .collect(Collectors.toList());
    }
}
