package com.blockchain.demo.dto;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public record TransactionEventDto(Long id,
                                  BigInteger blockNumber,
                                  String fromAddress,
                                  String toAddress,
                                  String hash,
                                  BigDecimal amount,
                                  BigDecimal gasPrice,
                                  BigDecimal translationFee,
                                  int blockConfirmation,
                                  TransactionEventStatus status,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt) {

}
