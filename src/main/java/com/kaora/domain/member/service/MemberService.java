package com.kaora.domain.member.service;

import com.kaora.domain.member.dto.LoginRequestDTO;
import com.kaora.domain.member.dto.SignUpRequestDTO;
import com.kaora.domain.member.entity.Member;

public interface MemberService {

    // 회원가입
    String signUp(SignUpRequestDTO signUpRequestDTO);

    // 로그인
    String login(LoginRequestDTO loginRequestDTO);

    // 회원정보 수정
    Member updateMemberInfo(Long memberId, String name, String password);

    // 이메일 기반 회원정보 조회
    Member findByEmail(String email);

    String refreshAccessToken(String refreshToken);
}
