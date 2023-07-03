package com.block.demo.dto.response;

import com.blockchain.demo.dto.response.WalletResponseDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletResponseDtoFactory {

    public static WalletResponseDto generate() {
        return new WalletResponseDto(1L,
                                     "0x00",
                                     BigDecimal.valueOf(0.5),
                                     LocalDateTime.now(),
                                     null);
    }
}
