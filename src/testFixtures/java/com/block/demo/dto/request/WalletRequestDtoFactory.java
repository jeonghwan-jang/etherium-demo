package com.block.demo.dto.request;

import com.blockchain.demo.dto.request.WalletRequestDto;

public class WalletRequestDtoFactory {

    public static WalletRequestDto generate() {
        return new WalletRequestDto("1234");
    }
}
