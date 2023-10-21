package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.LikesDislikesReplyService;
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
@RequestMapping("/api/vote/reply")
@RequiredArgsConstructor
public class LikesDislikesReplyController {

    private final LikesDislikesReplyService likesDislikesReplyService;
    @PostMapping("/up/{replyId}")
    public ResponseEntity<ResponseDto> userUpLikeReply(@PathVariable Long replyId, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likesDislikesReplyService.userUpLikeReply(replyId,currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/down/{replyId}")
    public ResponseEntity<ResponseDto> userDislikeReply(@PathVariable Long replyId, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = likesDislikesReplyService.userDislikeReply(replyId,currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
