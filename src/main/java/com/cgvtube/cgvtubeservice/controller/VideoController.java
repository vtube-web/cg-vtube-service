package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.service.impl.VideoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoServiceImpl videoService;
    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> getFirst40Videos() {
        List<VideoResponseDto> videoResponseDtoList = videoService.getFirst40Videos();
        return new ResponseEntity<>(videoResponseDtoList, HttpStatus.OK);
    }
}
