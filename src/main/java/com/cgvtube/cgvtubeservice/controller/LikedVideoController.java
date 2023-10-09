package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.LikedVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/liked-videos")
public class LikedVideoController {
    private final LikedVideoService likedVideoService;
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getAllLikedVideos(@PathVariable Long userId, Pageable pageable) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"likedAt");
        ResponseDto responseDto = likedVideoService.getLikedVideos(userId, pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
