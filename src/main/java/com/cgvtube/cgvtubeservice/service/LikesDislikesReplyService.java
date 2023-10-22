package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface LikesDislikesReplyService {
    ResponseDto userUpLikeReply(Long replyId, UserDetails currentUser);

    ResponseDto userDislikeReply(Long replyId, UserDetails currentUser);
}
