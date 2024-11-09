package com.kaora.domain.member.controller;

import com.kaora.domain.member.dto.LoginRequestDTO;
import com.kaora.domain.member.dto.SignUpRequestDTO;
import com.kaora.domain.member.dto.UpdateMemberRequestDTO;
import com.kaora.domain.member.entity.Member;
import com.kaora.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return ResponseEntity.ok(memberService.signUp(signUpRequestDTO));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(memberService.login(loginRequestDTO));
    }

    // 회원정보 수정
    @PutMapping("/update/{memberId}")
    public ResponseEntity<Member> updateMemberInfo(@PathVariable Long memberId,
                                                   @RequestBody UpdateMemberRequestDTO updateMemberRequestDTO) {
        Member updatedMember = memberService.updateMemberInfo(memberId,
                updateMemberRequestDTO.getName(), updateMemberRequestDTO.getPassword());
        return ResponseEntity.ok(updatedMember);
    }

    // 이메일로 회원 조회
    @GetMapping("/find/{email}")
    public ResponseEntity<Member> findByEmail(@PathVariable String email) {
        Member member = memberService.findByEmail(email);
        return ResponseEntity.ok(member);
    }
}
