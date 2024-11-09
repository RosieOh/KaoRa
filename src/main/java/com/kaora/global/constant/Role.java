package com.kaora.global.constant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    ADMIN("ROLE_ADMIN"),
    DEVELOPER("ROLE_DEVELOPER"),
    MEMBER("ROLE_MEMBER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name(); // Spring Security는 권한에 'ROLE_' 접두어를 추가해서 관리
    }
}
