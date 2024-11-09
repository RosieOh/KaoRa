package com.kaora.domain.member.service;

import com.kaora.domain.member.entity.Member;
import com.kaora.domain.member.repository.MemberRepository;
import com.kaora.global.jwt.entity.TokenProvider;
import com.kaora.domain.member.dto.LoginRequestDTO;
import com.kaora.domain.member.dto.SignUpRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // 회원가입
    @Override
    public String signUp(SignUpRequestDTO signUpRequestDTO) {
        // 이미 존재하는 이메일인지 확인
        Optional<Member> existingMember = memberRepository.findByEmail(signUpRequestDTO.getEmail());
        if (existingMember.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // 새로운 회원 생성
        Member member = new Member();
        member.setEmail(signUpRequestDTO.getEmail());
        member.setName(signUpRequestDTO.getName());
        member.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        member.setRole(signUpRequestDTO.getRole());  // 기본 ROLE 설정

        memberRepository.save(member);

        return "User successfully registered!";
    }

    // 로그인 처리 (AccessToken과 RefreshToken 발급)
    @Override
    public String login(LoginRequestDTO loginRequestDTO) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // AccessToken과 RefreshToken 생성
        String accessToken = tokenProvider.createAccessToken(member.getEmail(), member.getRole().name());
        String refreshToken = tokenProvider.createRefreshToken(member.getEmail());

        return "AccessToken: " + accessToken + "\nRefreshToken: " + refreshToken;
    }

    // 회원정보 수정
    @Override
    public Member updateMemberInfo(Long memberId, String name, String password) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 회원 정보 수정
        member.setName(name);
        member.setPassword(passwordEncoder.encode(password));  // 비밀번호는 암호화하여 저장
        memberRepository.save(member);

        return member;
    }

    // 이메일로 회원 조회
    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        return null;
    }
}
