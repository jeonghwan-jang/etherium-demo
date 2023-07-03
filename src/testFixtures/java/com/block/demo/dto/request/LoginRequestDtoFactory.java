package com.block.demo.dto.request;

import com.blockchain.demo.dto.request.LoginRequestDto;

public class LoginRequestDtoFactory {

    public static LoginRequestDto generate() {
        return new LoginRequestDto("jjh",
                                   "1234");
    }
}
