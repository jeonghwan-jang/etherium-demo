package com.block.demo.dto.response;

import com.blockchain.demo.common.constant.MemberRole;
import com.blockchain.demo.dto.response.MemberResponseDto;
import java.time.LocalDateTime;
import java.util.Collections;

public class MemberResponseDtoFactory {

    public static MemberResponseDto generate() {
        return new MemberResponseDto(1L,
                                     "jjh",
                                     "장정환",
                                     null,
                                     LocalDateTime.now(),
                                     null,
                                     Collections.singletonList(MemberRole.BASIC.toString()));
    }
}
