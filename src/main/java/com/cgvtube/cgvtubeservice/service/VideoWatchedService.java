package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;

public interface VideoWatchedService {

    ResponseDto findAllWatchedVideo(Long userId, Pageable pageableRequest);

    ResponseDto deleteWatchedVideo(Long userId, Long videoId) throws Exception;

    ResponseDto deleteWatchedVideosByUserId(Long userId) throws Exception;

}
