package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.CheckEmailReqDto;
import com.cgvtube.cgvtubeservice.payload.request.UserIdListReqDto;
import com.cgvtube.cgvtubeservice.payload.request.UserLoginRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.UserRegisterRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import com.cgvtube.cgvtubeservice.service.impl.CurrentUserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    CurrentUserServiceImpl getCurrentUser();

    void save(UserRegisterRequestDto userRegisterRequestDto);

    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);

    String refreshToken();

    ResponseDto checkValidEmail(CheckEmailReqDto emailReqDto);

    ResponseDto getUserInfo(UserDetails currentUser);

    ResponseDto getUserList(UserIdListReqDto userIdList);

}
