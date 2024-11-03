package com.kaora.domain.member.dto;

import com.kaora.global.constant.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomMemberInfoDTO extends MemberDTO{

    private Long memberId;

    private String email;

    private String name;

    private String password;

    private Role role;

}
