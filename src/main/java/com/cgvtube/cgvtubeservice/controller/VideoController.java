package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.service.VideoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> findAllVideos() {
        List<VideoResponseDto> videoResponseDtoList = videoService.findAllVideos();
        return new ResponseEntity<>(videoResponseDtoList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getVideo(@PathVariable("id") Long videoId, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.getVideoById(videoId, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addVideo(@RequestBody AddVideoReqDto addVideoReqDto, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.addVideo(addVideoReqDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateVideo(@RequestBody VideoUpdateReqDto videoUpdateReqDto, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.updateVideo(videoUpdateReqDto, currentUser);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subscribed")
    public ResponseEntity<ResponseDto> getAllVideosBySubscribedChannels(Pageable pageable, HttpSession session) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createAt");
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.findAllVideosBySubscribedChannels(currentUser, pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}

