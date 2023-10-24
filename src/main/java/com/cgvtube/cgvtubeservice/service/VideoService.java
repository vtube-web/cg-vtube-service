package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.*;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface VideoService {

    ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser);

    ResponseDto updateVideo(VideoUpdateReqDto videoUpdateReqDto, UserDetails currentUser);

    ResponseDto findAllVideos();

    ResponseDto getVideoById(Long videoId, UserDetails currentUser);

    ResponseDto findAllVideosBySubscribedChannels(UserDetails currentUser, Pageable pageableRequest);

    ResponseDto findAllByIdChannel(Pageable pageable,String title,String status,String views,Boolean isShort ,UserDetails currentUser);

    ResponseDto editFormVideoContent(String param, EditContentReqDto editContentReqDto, UserDetails currentUser);

    ResponseDto deleteVideoContent(DeleteContentReqDto deleteContentReqDto, UserDetails currentUser);

    ResponseDto getStatisticalVideosDateNew(Long userId);
}
