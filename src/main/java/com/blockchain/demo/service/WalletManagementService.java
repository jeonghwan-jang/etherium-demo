package com.blockchain.demo.service;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.service.WalletService;
import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.request.WalletRequestDto;
import com.blockchain.demo.dto.response.WalletResponseDto;
import com.blockchain.demo.repository.mapper.WalletMapper;
import com.blockchain.demo.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletManagementService {

    private static final String WALLET_DEFAULT_PATH = WalletUtils.getTestnetKeyDirectory();

    private final WalletService walletService;

    @Transactional
    public WalletResponseDto generateWallet(LoggedMemberDto member,
                                            WalletRequestDto requestDto) {
        boolean isDuplicated = this.walletService.existsByMemberId(member.id());
        if (isDuplicated) {
            throw new ErrorCodeException(ErrorCode.ALREADY_EXISTS_WALLET);
        }

        String walletPassword = requestDto.password();

        String walletBasePath = String.join(File.separator, WALLET_DEFAULT_PATH, member.username());
        File file = new File(walletBasePath);
        FileUtil.mkdirsIfNotExists(file);

        try {
            String walletFileName = WalletUtils.generateNewWalletFile(walletPassword, file);
            String walletFullPath = String.join(File.separator, walletBasePath, walletFileName);
            Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletFullPath);

            return WalletMapper.INSTANCE.toResponseDto(
                this.walletService.save(member.id(), walletFullPath, walletPassword, credentials.getAddress()));
        } catch (InvalidAlgorithmParameterException | CipherException | NoSuchAlgorithmException | IOException | NoSuchProviderException e) {
            log.error(e.getMessage(), e);
            throw new ErrorCodeException(ErrorCode.FAILED_GENERATE_WALLET);
        }
    }

    @Transactional(readOnly = true)
    public WalletResponseDto getWallet(LoggedMemberDto member) {
        return WalletMapper.INSTANCE.toResponseDto(
            this.walletService.findByMemberId(member.id())
        );
    }
}
