package com.blockchain.demo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberResponseDto(Long id,
                                String username,
                                String name,
                                LocalDateTime lastLoginDt,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt,
                                List<String> roles) {

}
