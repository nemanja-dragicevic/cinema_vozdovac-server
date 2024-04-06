package com.master.BioskopVozdovac.enums;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRole implements GrantedAuthority {

    ADMIN, USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
