package com.cgvtube.cgvtubeservice.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CurrentUserServiceImpl extends User {

    private Long id;
    private String userName;

    public CurrentUserServiceImpl(Long id, String email, String password, String userName, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id = id;
        this.userName = userName;
    }
}
