package com.block.demo.dto;

import com.blockchain.demo.dto.JwtTokenDto;

public class JwtTokenDtoFactory {

    public static JwtTokenDto generate() {
        return new JwtTokenDto(
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYW5nIiwiYXV0aG9yaXRpZXMiOiJST0xFX0JBU0lDIiwiZXhwIjoxNjg4Mzk3NTkwfQ.Rbwlv66-OQL1_PyezSs4oSJFKAf4Ho9tsADvepBhJfg",
            "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg5MTU5OTB9.MgbNtxPMfDSBObQDZeLbOvD1Z9fD9svt7nP_FdA5Rvw"
        );
    }
}
