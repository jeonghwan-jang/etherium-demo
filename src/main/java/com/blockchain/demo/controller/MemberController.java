package com.blockchain.demo.controller;

import com.blockchain.demo.dto.JwtTokenDto;
import com.blockchain.demo.dto.request.LoginRequestDto;
import com.blockchain.demo.dto.request.MemberRequestDto;
import com.blockchain.demo.dto.response.MemberResponseDto;
import com.blockchain.demo.service.MemberManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberManagementService memberManagementService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> generateMember(@RequestBody @Valid MemberRequestDto requestDto) {
        return ResponseEntity.ok(
            this.memberManagementService.generateMember(requestDto)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Valid LoginRequestDto requestDto) {
        return ResponseEntity.ok(
            this.memberManagementService.login(requestDto)
        );
    }
}
