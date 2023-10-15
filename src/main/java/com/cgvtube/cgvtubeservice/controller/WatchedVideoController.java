package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.impl.VideoWatchedServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watched-videos")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchedVideoController {
    private final VideoWatchedServiceImpl videoWatchedService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getWatchedVideos(@PathVariable Long userId, Pageable pageable) {
        Pageable pageableRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.Direction.DESC,
                "watchedAt");
        ResponseDto responseDto = videoWatchedService.findAllWatchedVideo(userId, pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto> deleteWatchedVideosByUserId(@PathVariable Long userId) throws Exception {
        ResponseDto responseDto = videoWatchedService.deleteWatchedVideosByUserId(userId);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{userId}/videos/{videoId}")
    public ResponseEntity<ResponseDto> deleteWatchedVideo(@PathVariable Long userId, @PathVariable Long videoId) throws Exception {
        ResponseDto responseDto = videoWatchedService.deleteWatchedVideo(userId, videoId);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
