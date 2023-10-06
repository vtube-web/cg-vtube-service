package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.Converter;
import com.cgvtube.cgvtubeservice.entity.Role;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.request.UserRegisterRequestDto;
import com.cgvtube.cgvtubeservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserRegisterConverter implements Converter<UserRegisterRequestDto, User> {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User convert(UserRegisterRequestDto source) {

        Role role = roleService.findById(1L);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        return User.builder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .userName(source.getUserName())
                .createdAt(LocalDateTime.now())
                .roles(roleSet)
                .build();
    }

    @Override
    public UserRegisterRequestDto revert(User target) {
        return null;
    }

    @Override
    public List<User> convert(List<UserRegisterRequestDto> sources) {
        return null;
    }

    @Override
    public List<UserRegisterRequestDto> revert(List<User> targets) {
        return null;
    }
}
