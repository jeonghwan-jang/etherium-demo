package com.blockchain.demo.domain.service;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.constant.MemberRole;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.domain.entity.Member;
import com.blockchain.demo.dto.LoggedMemberDto;
import com.blockchain.demo.dto.MemberDto;
import com.blockchain.demo.repository.MemberRepository;
import com.blockchain.demo.repository.mapper.MemberMapper;
import com.blockchain.demo.util.SecurityUtil;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDto findByUsername(String username) {
        return this.memberRepository.findByUsername(username)
            .map(MemberMapper.INSTANCE::toDto)
            .orElseThrow(() -> new UsernameNotFoundException("해당 회원을 찾을 수 없습니다."));
    }

    public MemberDto save(final String username,
                          final String password,
                          final String name) {
        Member member = this.memberRepository.save(
            MemberMapper.INSTANCE.of(username, password, name,
                                     Collections.singletonList(MemberRole.BASIC.toString()))
        );

        return MemberMapper.INSTANCE.toDto(member);
    }

    public boolean existsByUsername(String username) {
        return this.memberRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public LoggedMemberDto getLoginUserInfo() {
        final Object principal = SecurityUtil.getPrincipal();
        if (Objects.isNull(principal) || "anonymousUser".equals(principal)) {
            throw new ErrorCodeException(ErrorCode.UNAUTHORIZED);
        }

        UserDetails userDetails = (User) principal;
        return MemberMapper.INSTANCE.toLoggedDto(
            this.findByUsername(userDetails.getUsername())
        );
    }
}
