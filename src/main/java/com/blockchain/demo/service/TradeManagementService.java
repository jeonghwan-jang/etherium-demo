package com.blockchain.demo.service;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.service.TradeRequestHistoryService;
import com.blockchain.demo.domain.service.TransactionEventService;
import com.blockchain.demo.domain.service.WalletService;
import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.TransactionEventDto;
import com.blockchain.demo.dto.WalletDto;
import com.blockchain.demo.dto.request.PagingRequestDto;
import com.blockchain.demo.dto.request.WithdrawRequestDto;
import com.blockchain.demo.dto.response.WithdrawResponseDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

@Slf4j
@RequiredArgsConstructor
@Service
public class TradeManagementService {

    private final WalletService walletService;

    private final TradeRequestHistoryService tradeRequestHistoryService;

    private final TransactionEventService transactionEventService;

    private final Web3jService web3jService;

    @Transactional
    public WithdrawResponseDto withdraw(LoggedMemberDto member,
                                        WithdrawRequestDto requestDto) {
        Long memberId = member.id();
        WalletDto wallet = this.walletService.findByMemberId(memberId);

        String from = wallet.address();
        String to = requestDto.to();
        BigDecimal amount = requestDto.amount();
        BigDecimal requestAmount = this.tradeRequestHistoryService.getTotalRequestAmountByMemberId(memberId);
        BigDecimal gasPrice = Convert.fromWei(this.web3jService.getGasPrice().toString(), Unit.ETHER);
        String hash = null;

        boolean isInsufficient = wallet.amount().subtract(requestAmount).compareTo(amount.add(gasPrice)) < 0;
        if (isInsufficient) {
            throw new ErrorCodeException(ErrorCode.AMOUNT_INSUFFICIENT);
        }

        try {
            Credentials credentials = WalletUtils.loadCredentials(wallet.password(), wallet.path());
            hash = this.web3jService.sendSimpleTransaction(credentials, to, amount);

            this.tradeRequestHistoryService.save(memberId, hash, from, to, amount);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ErrorCodeException(ErrorCode.FAILED_WITHDRAW);
        }

        return new WithdrawResponseDto(hash, from, to, amount);
    }

    @Transactional(readOnly = true)
    public List<TransactionEventDto> getTradeHistories(LoggedMemberDto member,
                                                       PagingRequestDto requestDto) {
        WalletDto wallet = this.walletService.findByMemberId(member.id());
        return this.transactionEventService.getTransactionEvents(wallet.address(), requestDto);
    }
}
