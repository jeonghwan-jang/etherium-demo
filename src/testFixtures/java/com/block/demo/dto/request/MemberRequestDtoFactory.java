package com.block.demo.dto.request;

import com.blockchain.demo.dto.request.MemberRequestDto;

public class MemberRequestDtoFactory {

    public static MemberRequestDto generate() {
        return new MemberRequestDto("jjh",
                                    "1234",
                                    "장정환");
    }
}
