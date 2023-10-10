package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;

public interface LikedVideoService {
    ResponseDto getLikedVideos(Long userId, Pageable pageableRequest);

}
