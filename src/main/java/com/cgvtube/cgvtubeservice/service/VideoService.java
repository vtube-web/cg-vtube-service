package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
public interface VideoService {
    ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser);
    ResponseDto updateVideo(VideoUpdateReqDto videoUpdateReqDto, UserDetails currentUser);
    ResponseDto findAllVideos();
    ResponseDto getVideoById(Long videoId, UserDetails currentUser);

    ResponseDto findAllVideosBySubscribedChannels(UserDetails currentUser, Pageable pageableRequest);

}
