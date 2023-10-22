package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserLoginResponseDtoConverter implements Function<User, UserLoginResponseDto> {
    @Override
    public UserLoginResponseDto apply(User user) {
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        BeanUtils.copyProperties(user,userLoginResponseDto);
        return userLoginResponseDto;
    }
}
