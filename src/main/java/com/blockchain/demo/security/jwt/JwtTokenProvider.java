package com.blockchain.demo.security.jwt;

import com.blockchain.demo.common.constant.ErrorCode;
import com.blockchain.demo.common.exception.ErrorCodeException;
import com.blockchain.demo.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final int ACCESS_TOKEN_VALID_MILLI_SECONDS = 60 * 60 * 24 * 1000;
    private static final int REFRESH_TOKEN_VALID_MILLI_SECONDS = 60 * 60 * 24 * 7 * 1000;

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenDto generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
            .setSubject(authentication.getName())
            .claim("authorities", authorities)
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_MILLI_SECONDS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        String refreshToken = Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_MILLI_SECONDS))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        return new JwtTokenDto(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = this.parseClaims(accessToken);

        if (Objects.isNull(claims.get("authorities"))) {
            throw new ErrorCodeException(ErrorCode.UNAUTHORIZED);
        }

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get("authorities").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
