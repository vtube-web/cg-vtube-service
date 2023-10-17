package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.VideoWatchedService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watched-videos")
public class WatchedVideoController {
    private final VideoWatchedService videoWatchedService;

    @GetMapping
    public ResponseEntity<ResponseDto> getWatchedVideos(Pageable pageable, HttpSession session) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "watchedAt");
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoWatchedService.findAllWatchedVideo(currentUser, pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteWatchedVideosByUserId(HttpSession session) throws Exception {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoWatchedService.deleteWatchedVideosByUserId(currentUser);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<ResponseDto> deleteWatchedVideo(@PathVariable Long videoId, HttpSession session) throws Exception {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");

        ResponseDto responseDto = videoWatchedService.deleteWatchedVideo(currentUser, videoId);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
