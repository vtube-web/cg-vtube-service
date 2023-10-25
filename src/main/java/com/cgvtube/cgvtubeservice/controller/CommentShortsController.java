package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.CommentShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentShortsController {
    private final CommentService commentService;

    @PostMapping("/{videoId}/commentShorts")
    public ResponseEntity<ResponseDto> saveComment(@PathVariable("videoId") Long videoId,
                                                   @RequestBody CommentShortsRequestDto commentShortsRequestDto) {
        ResponseDto responseDto = commentService.save(videoId, commentShortsRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
