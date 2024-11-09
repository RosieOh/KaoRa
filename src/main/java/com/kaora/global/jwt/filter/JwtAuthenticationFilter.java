package com.kaora.global.jwt.filter;

import com.kaora.global.jwt.entity.TokenProvider;
import com.kaora.domain.member.entity.Member;
import com.kaora.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository; // MemberRepository를 직접 주입받아 사용

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Request에서 JWT 토큰을 추출합니다.
        String token = getTokenFromRequest(request);

        // 토큰이 존재하고 유효하면 인증 처리
        if (token != null && tokenProvider.validateToken(token, tokenProvider.getEmailFromToken(token))) {
            // 토큰에서 이메일 추출
            String email = tokenProvider.getEmailFromToken(token);

            // 이메일로 사용자를 조회
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 인증 객체 생성 (UsernamePasswordAuthenticationToken 사용)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            member.getEmail(), null, member.getAuthorities());  // authorities를 사용

            // 인증 정보 설정
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 인증 정보를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 필터 체인 다음 필터로 이동
        chain.doFilter(request, response);
    }

    // 요청에서 "Authorization" 헤더에서 Bearer 토큰을 추출하는 메소드
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
