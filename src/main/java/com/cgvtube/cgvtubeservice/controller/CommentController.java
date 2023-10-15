package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{videoId}/comment")
    public ResponseEntity<ResponseDto> saveComment(@PathVariable("videoId") Long videoId,
                                                   @RequestBody CommentRequestDto commentRequestDto) {
        ResponseDto responseDto = commentService.save(videoId, commentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable("id") Long commentId) {
        ResponseDto responseDto = commentService.delete(commentId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}