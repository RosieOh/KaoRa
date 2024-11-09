//package com.kaora.global.jwt.util;
//
//
//import com.kaora.global.jwt.exception.InvalidJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Log4j2
//@Component
//public class JwtUtil {
//
//    @Value("${jwt.access-token-expiration}")
//    private long accessTokenExpirationTime;  // 예: 15분
//
//    @Value("${jwt.refresh-token-expiration}")
//    private long refreshTokenExpirationTime;  // 예: 30일
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    private static final String JWT_PREFIX = "Bearer ";
//
//    // AccessToken 생성
//    public String createAccessToken(String email, String role) {
//        return createToken(email, role, accessTokenExpirationTime);
//    }
//
//    // RefreshToken 생성
//    public String createRefreshToken(String email) {
//        return createToken(email, null, refreshTokenExpirationTime);
//    }
//
//    // 공통 토큰 생성 메서드
//    private String createToken(String email, String role, long expirationTime) {
//        var builder = Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS256, secretKey);
//
//        // role이 있을 경우에만 추가
//        if (role != null) {
//            builder.claim("role", role);
//        }
//
//        return builder.compact();
//    }
//
//    // 토큰에서 이메일 추출
//    public String getEmailFromToken(String token) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(cleanToken(token))
//                    .getBody()
//                    .getSubject();
//        } catch (Exception e) {
//            log.error("Invalid JWT token: " + token, e);
//            throw new InvalidJwtException("Invalid or expired JWT token.");
//        }
//    }
//
//    // 토큰에서 역할(role) 추출
//    public String getRoleFromToken(String token) {
//        try {
//            return (String) Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(cleanToken(token))
//                    .getBody()
//                    .get("role");
//        } catch (Exception e) {
//            log.error("Invalid JWT token: " + token, e);
//            throw new RuntimeException("Invalid JWT token.");
//        }
//    }
//
//    // 토큰 만료 여부 확인
//    public boolean isTokenExpired(String token) {
//        return getExpirationDateFromToken(token).before(new Date());
//    }
//
//    private Date getExpirationDateFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(cleanToken(token))
//                .getBody()
//                .getExpiration();
//    }
//
//    // 토큰 유효성 검증
//    public boolean validateToken(String token, String email) {
//        return email.equals(getEmailFromToken(token)) && !isTokenExpired(token);
//    }
//
//    private String cleanToken(String token) {
//        if (token != null && token.startsWith(JWT_PREFIX)) {
//            return token.substring(JWT_PREFIX.length());
//        } else {
//            throw new RuntimeException("Token does not start with Bearer prefix");
//        }
//    }
//
//    // Refresh Token을 사용하여 Access Token 재발급
//    public String refreshAccessToken(String refreshToken) {
//        String email = getEmailFromToken(refreshToken);
//
//        if (isTokenExpired(refreshToken)) {
//            throw new InvalidJwtException("Refresh token is expired.");
//        }
//
//        // refreshToken이 유효하다면 새로운 accessToken 발급
//        return createAccessToken(email, getRoleFromToken(refreshToken));
//    }
//
//}