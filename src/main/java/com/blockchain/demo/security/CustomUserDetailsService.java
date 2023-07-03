package com.blockchain.demo.security;

import com.blockchain.demo.domain.service.MemberService;
import com.blockchain.demo.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.createUserDetails(
            this.memberService.findByUsername(username)
        );
    }

    private UserDetails createUserDetails(MemberDto member) {
        return User.builder()
            .username(member.username())
            .password(member.password())
            .roles(member.roles().toArray(String[]::new))
            .build();
    }
}
