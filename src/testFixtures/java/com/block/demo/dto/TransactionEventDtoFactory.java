package com.block.demo.dto;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.dto.TransactionEventDto;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class TransactionEventDtoFactory {

    public static TransactionEventDto generate() {
        return new TransactionEventDto(
            1L,
            BigInteger.valueOf(853962),
            "0x00",
            "0x00",
            "0x00",
            BigDecimal.valueOf(0.5),
            BigDecimal.valueOf(0.0001),
            BigDecimal.valueOf(0.001),
            0,
            TransactionEventStatus.MINED,
            LocalDateTime.now(),
            null
        );
    }
}
