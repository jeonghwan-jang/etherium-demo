package com.blockchain.demo.controller;

import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.TransactionEventDto;
import com.blockchain.demo.dto.request.PagingRequestDto;
import com.blockchain.demo.dto.request.WithdrawRequestDto;
import com.blockchain.demo.dto.response.WithdrawResponseDto;
import com.blockchain.demo.security.annotation.LoggedMember;
import com.blockchain.demo.service.TradeManagementService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/trades")
@RestController
public class TradeController {

    private final TradeManagementService tradeManagementService;

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponseDto> withdraw(@LoggedMember LoggedMemberDto member,
                                                        @RequestBody @Valid WithdrawRequestDto requestDto) {
        return ResponseEntity.ok(
            this.tradeManagementService.withdraw(member, requestDto)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<TransactionEventDto>> getTradeHistories(@LoggedMember LoggedMemberDto member,
                                                                       PagingRequestDto requestDto) {
        return ResponseEntity.ok(
            this.tradeManagementService.getTradeHistories(member, requestDto)
        );
    }
}
