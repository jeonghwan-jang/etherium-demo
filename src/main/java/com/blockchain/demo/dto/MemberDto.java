package com.blockchain.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MemberDto(Long id,
                        String username,
                        String password,
                        String name,
                        LocalDateTime lastLoginDt,
                        List<String> roles,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {

}
