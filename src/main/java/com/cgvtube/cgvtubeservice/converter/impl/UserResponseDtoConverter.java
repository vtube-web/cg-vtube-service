package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.Converter;
import com.cgvtube.cgvtubeservice.entiny.User;
import com.cgvtube.cgvtubeservice.payload.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserResponseDtoConverter implements Converter<UserResponseDto, User> {

    @Override
    public User convert(UserResponseDto source) {
        return null;
    }

    @Override
    public UserResponseDto revert(User target) {

        return UserResponseDto.builder()
                .id(target.getId())
                .userName(target.getUserName())
                .avatar(target.getAvatar())
                .build();
    }

    @Override
    public List<User> convert(List<UserResponseDto> sources) {
        return null;
    }

    @Override
    public List<UserResponseDto> revert(List<User> targets) {
        return null;
    }
}
