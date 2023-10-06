package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.entiny.User;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.VideoService;
import com.cgvtube.cgvtubeservice.service.impl.UserImpl;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos/")
@AllArgsConstructor
public class VideoController {
    private VideoService videoService;

    @PostMapping("add")
    public ResponseEntity<ResponseDto> addVideo(@RequestBody AddVideoReqDto addVideoReqDto, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.addVideo(addVideoReqDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @PutMapping("update")
    public ResponseEntity<ResponseDto> updateVideo(@RequestBody VideoUpdateReqDto videoUpdateReqDto, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.updateVideo(videoUpdateReqDto,currentUser);
        if(responseDto.getStatus() == "200"){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
