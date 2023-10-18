package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/comment")
    public ResponseEntity<ResponseDto> getCommentByChannel(@PageableDefault(value = 5) Pageable pageable, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = commentService.getCommentByChannel(pageable,currentUser);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}