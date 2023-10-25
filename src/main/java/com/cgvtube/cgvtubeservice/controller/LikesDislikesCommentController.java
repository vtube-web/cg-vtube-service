package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.LikesDislikesCommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote/comment")
@RequiredArgsConstructor
public class LikesDislikesCommentController {
    private final LikesDislikesCommentService likesDislikesCommentService;
    @PostMapping("/up/{commentId}")
    public ResponseEntity<ResponseDto> userUpLikeComment(@PathVariable Long commentId, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likesDislikesCommentService.userUpLikeComment(commentId,currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/down/{commentId}")
    public ResponseEntity<ResponseDto> userDislikeComment(@PathVariable Long commentId, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likesDislikesCommentService.userDislikeComment(commentId,currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
