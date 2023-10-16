package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface LikedVideoService {
    ResponseDto getLikedVideos(UserDetails currentUser, Pageable pageableRequest);

    ResponseDto deleteLikedVideo(Long videoId, UserDetails currentUser);

}
