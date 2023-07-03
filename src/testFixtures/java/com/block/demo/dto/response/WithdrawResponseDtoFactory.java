package com.block.demo.dto.response;

import com.blockchain.demo.dto.response.WithdrawResponseDto;
import java.math.BigDecimal;

public class WithdrawResponseDtoFactory {

    public static WithdrawResponseDto generate() {
        return new WithdrawResponseDto("0x00",
                                       "0x00",
                                       "0x00",
                                       BigDecimal.valueOf(0.5));
    }
}
