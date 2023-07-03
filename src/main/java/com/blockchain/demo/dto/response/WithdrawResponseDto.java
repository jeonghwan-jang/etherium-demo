package com.blockchain.demo.dto.response;

import java.math.BigDecimal;

public record WithdrawResponseDto(String hash,
                                  String from,
                                  String to,
                                  BigDecimal amount) {

}
