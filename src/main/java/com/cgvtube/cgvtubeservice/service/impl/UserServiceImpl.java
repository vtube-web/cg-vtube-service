package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.configuration.security.JwtTokenProvider;
import com.cgvtube.cgvtubeservice.converter.impl.UserInfoConverter;
import com.cgvtube.cgvtubeservice.converter.impl.UserRegisterConverter;
import com.cgvtube.cgvtubeservice.converter.impl.UserResponseConverter;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.request.*;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.UserResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterConverter userRegisterConverter;
    private final UserResponseConverter userResponseConverter;
    private final UserInfoConverter userInfoConverter;
    private final Function<User, UserLoginResponseDto> responseDtoFunction;

    @Override
    public CurrentUserServiceImpl getCurrentUser() {
        try {
            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CurrentUserServiceImpl user = (CurrentUserServiceImpl) object;
            return user;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username");
        }
        return user.get();
    }

    @Override
    public void save(UserRegisterRequestDto userRegisterRequestDto) {
        userRepository.save(userRegisterConverter.convert(userRegisterRequestDto));
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = loadUserByEmail(userLoginRequestDto.getEmail());

        String token = jwtUtil.generateToken(authentication);
        String refreshToken = jwtUtil.generateRefreshToken(authentication);

        return UserLoginResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .name(user.getChannelName())
                .avatar(user.getAvatar())
                .banner(user.getBanner())
                .description(user.getDescription())
                .channelName(user.getChannelName())
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return jwtUtil.generateToken(authentication);
    }

    @Override
    public ResponseDto  checkValidEmail(CheckEmailReqDto emailReqDto) {

        Optional<User> user = userRepository.findByEmail(emailReqDto.getEmail());
        ResponseDto responseDto;
        if (user.isEmpty()) {
            responseDto = ResponseDto.builder()
                    .message("can use email")
                    .status("200")
                    .data(true)
                    .build();
        } else {
            responseDto = ResponseDto.builder()
                    .message("can't use email")
                    .status("409").
                    data(false)
                    .build();
        }
        return responseDto;
    }

    @Override
    public ResponseDto getUserInfo(UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        return ResponseDto.builder()
                .message("Successful get info userId: " + user.getId())
                .status("200")
                .data(userInfoConverter.revert(user))
                .build();
    }
    @Override
    public ResponseDto getUserInfoByUserName(String userName) {
        User user = userRepository.findByUserName(userName).orElse(new User());
        return ResponseDto.builder()
                .message("Successful get info user By userName: " +user.getUserName())
                .status("200")
                .data(userInfoConverter.revert(user))
                .build();
    }
    @Override
    public ResponseDto getUserList(UserIdListReqDto userIdList) {
        List<User> userList = userRepository.findAllById(userIdList.getUserIdList());
        return ResponseDto.builder()
                .message("Successful get list user")
                .status("200").
                data(userInfoConverter.revert(userList))
                .build();
    }
    @Override
    public ResponseDto editUserProfile(UserEditProfileReqDto userEditProfileReqDto, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(new User());
        if (user == null || !user.getId().equals(userEditProfileReqDto.getId())){
            return ResponseDto.builder().message("user author error").status("401").data(false).build();
        }
        user.setUserName(userEditProfileReqDto.getUserName());
        user.setChannelName(userEditProfileReqDto.getChannelName());
        user.setDescription(userEditProfileReqDto.getDescription());
        User userResult = userRepository.save(user);
        return ResponseDto.builder().message("Edit success").status("200").data(responseDtoFunction.apply(userResult)).build();
    }

    @Override
    public ResponseDto editUserAvatar(UserEditAvatarReqDto userEditAvatarReqDto, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(new User());
        if (user == null || !user.getId().equals(userEditAvatarReqDto.getId())){
            return ResponseDto.builder().message("user author error").status("401").data(false).build();
        }
        user.setAvatar(userEditAvatarReqDto.getAvatar());
        userRepository.save(user);
        return ResponseDto.builder().message("Edit success").status("200").data(true).build();
    }

    @Override
    public ResponseDto editUserBanner(UserEditBannerReqDto userEditBannerReqDto, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(new User());
        if (user == null || !user.getId().equals(userEditBannerReqDto.getId())){
            return ResponseDto.builder().message("user author error").status("401").data(false).build();
        }
        user.setBanner(userEditBannerReqDto.getBanner());
        userRepository.save(user);
        return ResponseDto.builder().message("Edit success").status("200").data(true).build();
    }

}
