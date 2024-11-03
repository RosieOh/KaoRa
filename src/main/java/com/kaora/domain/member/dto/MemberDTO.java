package com.kaora.domain.member.dto;

import com.kaora.global.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long memberId;
    private String email;
    private String name;
    private String password;
    private Role role;
}