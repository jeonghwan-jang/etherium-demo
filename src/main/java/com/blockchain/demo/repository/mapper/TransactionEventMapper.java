package com.blockchain.demo.repository.mapper;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.TransactionEvent;
import com.blockchain.demo.dto.TransactionEventDto;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionEventMapper {

    TransactionEventMapper INSTANCE = Mappers.getMapper(TransactionEventMapper.class);

    TransactionEvent of(BigInteger blockNumber,
                        String fromAddress,
                        String toAddress,
                        String hash,
                        BigDecimal amount,
                        BigDecimal gasPrice,
                        BigDecimal translationFee,
                        int blockConfirmation,
                        TransactionEventStatus status);

    TransactionEventDto toDto(TransactionEvent transactionEvent);
}
