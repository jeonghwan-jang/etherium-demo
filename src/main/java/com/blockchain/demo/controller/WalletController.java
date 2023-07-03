package com.blockchain.demo.controller;

import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.request.WalletRequestDto;
import com.blockchain.demo.dto.response.WalletResponseDto;
import com.blockchain.demo.security.annotation.LoggedMember;
import com.blockchain.demo.service.WalletManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
@RestController
public class WalletController {

    private final WalletManagementService walletManagementService;

    @PostMapping
    public ResponseEntity<WalletResponseDto> generateWallet(@LoggedMember LoggedMemberDto member,
                                                            @RequestBody @Valid WalletRequestDto requestDto) {
        return ResponseEntity.ok(
            this.walletManagementService.generateWallet(member, requestDto)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<WalletResponseDto> getWallet(@LoggedMember LoggedMemberDto member) {
        return ResponseEntity.ok(
            this.walletManagementService.getWallet(member)
        );
    }
}
