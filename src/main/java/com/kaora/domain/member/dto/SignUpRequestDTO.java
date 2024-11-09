package com.kaora.domain.member.dto;

import com.kaora.global.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {
    private String email;
    private String name;
    private String password;
    private Role role;

}
