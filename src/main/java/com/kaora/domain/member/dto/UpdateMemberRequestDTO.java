package com.kaora.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 회원 정보 수정
public class UpdateMemberRequestDTO {

    private String name;
    private String password;
}