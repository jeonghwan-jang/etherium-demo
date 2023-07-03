package com.blockchain.demo.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public record WalletResponseDto(Long id,
                                String address,
                                BigDecimal amount,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt) {

    public WalletResponseDto {
        if (Objects.isNull(amount)) {
            amount = BigDecimal.ZERO;
        }
    }
}
