package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.LikeOrDislikeReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.LikedVideoService;
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
@RequestMapping("/api/liked-videos")
public class LikedVideoController {
    private final LikedVideoService likedVideoService;
    @GetMapping
    public ResponseEntity<ResponseDto> getAllLikedVideos(Pageable pageable, HttpSession session) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"likedAt");
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likedVideoService.getLikedVideos(currentUser, pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{videoId}")
    public ResponseEntity<ResponseDto> deleteLikedVideo(@PathVariable Long videoId, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likedVideoService.deleteLikedVideo(videoId, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addLikeVideo (@RequestBody LikeOrDislikeReqDto likeOrDislikeReqDto, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likedVideoService.addLikeOrDislikeVideo(likeOrDislikeReqDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
