package com.cgvtube.cgvtubeservice.service.impl;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserImpl extends User {
    private Long id;
    private String userName;

    public UserImpl(Long id, String email, String password, String userName, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.id = id;
        this.userName = userName;
    }
}
