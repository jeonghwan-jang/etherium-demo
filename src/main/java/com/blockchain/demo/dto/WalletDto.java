package com.blockchain.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletDto(Long id,
                        Long memberId,
                        String path,
                        String password,
                        String address,
                        BigDecimal amount,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {

}
