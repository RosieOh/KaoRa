package com.kaora.global.jwt.entity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpirationTime;

    private static final String JWT_PREFIX = "Bearer ";

    // AccessToken 생성
    public String createAccessToken(String email, String role) {
        return createToken(email, role, accessTokenExpirationTime);
    }

    // RefreshToken 생성
    public String createRefreshToken(String email) {
        return createToken(email, null, refreshTokenExpirationTime);
    }

    // 공통 토큰 생성 메서드
    private String createToken(String email, String role, long expirationTime) {
        var builder = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey);

        if (role != null) {
            builder.claim("role", role);  // role을 토큰에 추가
        }

        return builder.compact();
    }

    // 토큰에서 이메일 추출
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace(JWT_PREFIX, "")) // Bearer 접두어 제거
                .getBody()
                .getSubject();
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace(JWT_PREFIX, ""))
                .getBody()
                .getExpiration();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token, String email) {
        return email.equals(getEmailFromToken(token)) && !isTokenExpired(token);
    }
}
