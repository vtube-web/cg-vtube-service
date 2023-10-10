package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.configuration.security.JwtTokenProvider;
import com.cgvtube.cgvtubeservice.converter.impl.UserRegisterConverter;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.request.CheckEmailReqDto;
import com.cgvtube.cgvtubeservice.payload.request.UserLoginRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.UserRegisterRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRegisterConverter userRegisterConverter;
    @Override
    public CurrentUserServiceImpl getCurrentUser() {
        try {
            return (CurrentUserServiceImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
    public ResponseDto checkValidEmail(CheckEmailReqDto emailReqDto) {

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
}
