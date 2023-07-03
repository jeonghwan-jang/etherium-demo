package com.blockchain.demo.dto.request;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import java.util.Objects;

// TODO: 여유 되면 resolve 등록
public record PagingRequestDto(Long startingAfter,
                               Long endingBefore,
                               Integer size) {

    public PagingRequestDto {
        if (Objects.isNull(size)) {
            size = 10;
        } else if (100 < size) {
            throw new ErrorCodeException(ErrorCode.OVER_PAGE_SIZE);
        }
    }
}
