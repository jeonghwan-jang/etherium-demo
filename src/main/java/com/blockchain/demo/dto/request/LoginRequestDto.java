package com.blockchain.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequestDto(@NotEmpty String username,
                              @NotEmpty String password) {

}
