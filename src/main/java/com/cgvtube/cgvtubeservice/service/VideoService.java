package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entiny.User;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface VideoService {
    ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser);

    ResponseDto updateVideo(VideoUpdateReqDto videoUpdateReqDto, UserDetails currentUser);
}
