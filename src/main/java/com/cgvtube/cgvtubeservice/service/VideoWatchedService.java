package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface VideoWatchedService {

    ResponseDto findAllWatchedVideo(UserDetails currentUser, Pageable pageableRequest);

    ResponseDto deleteWatchedVideo(UserDetails currentUser, Long videoId) throws Exception;

    ResponseDto deleteWatchedVideosByUserId(UserDetails currentUser) throws Exception;

}
