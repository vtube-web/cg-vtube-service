package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserResponseConverter implements GeneralConverter<UserResponseDto, User> {

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
                .channelName(target.getChannelName())
                .subscribers(target.getSubscribers())
                .build();
    }

    @Override
    public List<User> convert(List<UserResponseDto> sources) {
        return null;
    }

    @Override
    public List<UserResponseDto> revert(List<User> targets) {
        return targets.stream()
                .map(this::revert)
                .collect(Collectors.toList());
    }
}
