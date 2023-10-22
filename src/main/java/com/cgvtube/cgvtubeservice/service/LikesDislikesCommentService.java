package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface LikesDislikesCommentService {
    ResponseDto userUpLikeComment(Long commentId, UserDetails currentUser);

    ResponseDto userDislikeComment(Long commentId, UserDetails currentUser);
}
