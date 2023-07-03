package com.blockchain.demo.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record MemberRequestDto(@NotEmpty String username,
                               @NotEmpty String password,
                               @NotEmpty String name) {

}
