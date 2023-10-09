package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.UserLoginRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.UserLoginResponseDto;
import com.cgvtube.cgvtubeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<UserLoginResponseDto> authentication(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            UserLoginResponseDto userLoginResponseDto = userService.authenticate(userLoginRequestDto);
            System.out.println(userService.getCurrentUser());
            return new ResponseEntity<>(userLoginResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/auth/refresh-token")
    public ResponseEntity<?> refreshToken() {
        String accessToken = userService.refreshToken();
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }
}
