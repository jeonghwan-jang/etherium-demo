package com.blockchain.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WithdrawRequestDto(@NotEmpty String to,
                                 @NotNull BigDecimal amount) {

}
