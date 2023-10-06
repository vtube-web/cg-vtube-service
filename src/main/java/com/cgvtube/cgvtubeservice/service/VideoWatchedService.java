package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.WatchedVideoDTO;
import org.springframework.data.domain.Pageable;

public interface VideoWatchedService {

    PageResponseDTO<WatchedVideoDTO> findAllWatchedVideo(Long userId, Pageable pageableRequest);

    void deleteWatchedVideo(Long userId, Long videoId) throws Exception;

    void deleteWatchedVideosByUserId(Long userId) throws Exception;

}
