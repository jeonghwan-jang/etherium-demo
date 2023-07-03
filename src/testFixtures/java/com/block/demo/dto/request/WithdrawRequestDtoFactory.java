package com.block.demo.dto.request;

import com.blockchain.demo.dto.request.WithdrawRequestDto;
import java.math.BigDecimal;

public class WithdrawRequestDtoFactory {

    public static WithdrawRequestDto generate() {
        return new WithdrawRequestDto("0x00",
                                      BigDecimal.valueOf(0.5));
    }
}
