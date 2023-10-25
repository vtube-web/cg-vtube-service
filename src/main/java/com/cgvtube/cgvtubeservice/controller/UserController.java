package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.*;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody
                                      UserRegisterRequestDto userRegisterRequestDto,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
        } else {
            try {
                userService.save(userRegisterRequestDto);
            } catch (Exception exception) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/check_email")
    public ResponseEntity<ResponseDto> checkEmailIsExist(@RequestBody CheckEmailReqDto emailReqDto) {
        ResponseDto responseDto = userService.checkValidEmail(emailReqDto);
      return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getUserInfo(HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = userService.getUserInfo(currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/{userName}")
    public ResponseEntity<ResponseDto> getUserInfoByUserName(@PathVariable("userName") String userName) {
        ResponseDto responseDto = userService.getUserInfoByUserName(userName);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/list-user")
    public ResponseEntity<ResponseDto> getListUser(@RequestBody UserIdListReqDto userIdList) {
        ResponseDto responseDto = userService.getUserList(userIdList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ResponseDto> editUserProfile(@RequestBody UserEditProfileReqDto userEditProfileReqDto, HttpSession session){
        UserDetails userDetails = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = userService.editUserProfile(userEditProfileReqDto,userDetails);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PutMapping("/edit-avatar")
    public ResponseEntity<ResponseDto> editUserAvatar(@RequestBody UserEditAvatarReqDto userEditAvatarReqDto, HttpSession session){
        UserDetails userDetails = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = userService.editUserAvatar(userEditAvatarReqDto,userDetails);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @PutMapping("/edit-banner")
    public ResponseEntity<ResponseDto> editUserBanner(@RequestBody UserEditBannerReqDto userEditBannerReqDto, HttpSession session){
        UserDetails userDetails = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = userService.editUserBanner(userEditBannerReqDto,userDetails);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

}


