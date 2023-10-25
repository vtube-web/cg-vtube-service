package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
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
public class UserRegisterConverter implements GeneralConverter<UserRegisterRequestDto, User> {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    private static final String AVATARDEFAULT = "https://firebasestorage.googleapis.com/v0/b/vtube-15.appspot.com/o/images%2F387123399_317289870909894_6318809251513139950_n.jpg?alt=media&token=9a676663-abbe-4324-aba8-a634e63b305c&_gl=1*1vll957*_ga*MTE0NzY2MDExNy4xNjkxMDI4NDc2*_ga_CW55HF8NVT*MTY5NzEyNTg4NC4yOC4xLjE2OTcxMjU5MjAuMjQuMC4w";

    private static final String BANNERDEFAULT = "https://images.unsplash.com/photo-1486597622250-f997d79907f8?w=1400&h=600&fit=crop";
    @Override
    public User convert(UserRegisterRequestDto source) {
        Role role = roleService.findById(1L);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        return User.builder()
                .email(source.getEmail())
                .password(passwordEncoder.encode(source.getPassword()))
                .userName(source.getUserName())
                .channelName(source.getChannelName())
                .avatar(AVATARDEFAULT)
                .banner(BANNERDEFAULT)
                .description("")
                .subscribers(0L)
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
