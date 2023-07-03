package com.blockchain.demo.service;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.service.MemberService;
import com.blockchain.demo.dto.JwtTokenDto;
import com.blockchain.demo.dto.request.LoginRequestDto;
import com.blockchain.demo.dto.request.MemberRequestDto;
import com.blockchain.demo.dto.response.MemberResponseDto;
import com.blockchain.demo.repository.mapper.MemberMapper;
import com.blockchain.demo.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberManagementService {

    private final MemberService memberService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto generateMember(MemberRequestDto requestDto) {
        String username = requestDto.username();
        if (this.memberService.existsByUsername(username)) {
            throw new ErrorCodeException(ErrorCode.ALREADY_EXISTS_USERNAME);
        }

        String password = this.passwordEncoder.encode(requestDto.password());
        return MemberMapper.INSTANCE.toResponseDto(this.memberService.save(username, password, requestDto.name()));
    }

    @Transactional(readOnly = true)
    public JwtTokenDto login(LoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            requestDto.username(), requestDto.password());

        Authentication authentication = this.authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);

        return this.jwtTokenProvider.generateToken(authentication);
    }
}
