package com.blockchain.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record WalletRequestDto(@NotEmpty String password) {

}
