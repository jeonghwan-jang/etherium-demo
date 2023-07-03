package com.blockchain.demo.dto;

import java.util.List;

public record LoggedMemberDto(Long id,
                              String username,
                              String name,
                              List<String> roles) {

}
