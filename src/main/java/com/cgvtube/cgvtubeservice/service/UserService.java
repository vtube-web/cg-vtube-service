package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.CheckEmailReqDto;
import com.cgvtube.cgvtubeservice.payload.request.UserLoginRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.UserRegisterRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import com.cgvtube.cgvtubeservice.service.impl.UserImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserImpl getCurrentUser();

    void save(UserRegisterRequestDto userRegisterRequestDto);

    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);

    String refreshToken();

    ResponseDto checkValidEmail(CheckEmailReqDto emailReqDto);

    ResponseDto getUserInfo(UserDetails currentUser);

}
