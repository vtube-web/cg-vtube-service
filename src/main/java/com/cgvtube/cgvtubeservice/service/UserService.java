package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.*;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import com.cgvtube.cgvtubeservice.service.impl.CurrentUserServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    CurrentUserServiceImpl getCurrentUser();

    void save(UserRegisterRequestDto userRegisterRequestDto);

    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);

    String refreshToken();

    ResponseDto checkValidEmail(CheckEmailReqDto emailReqDto);

    ResponseDto getUserInfo(UserDetails currentUser);

    ResponseDto getUserInfoByUserName(String userName);

    ResponseDto getUserList(UserIdListReqDto userIdList);

    ResponseDto editUserProfile(UserEditProfileReqDto userEditProfileReqDto, UserDetails userDetails);

    ResponseDto editUserAvatar(UserEditAvatarReqDto userEditAvatarReqDto, UserDetails userDetails);

    ResponseDto editUserBanner(UserEditBannerReqDto userEditBannerReqDto, UserDetails userDetails);
}
