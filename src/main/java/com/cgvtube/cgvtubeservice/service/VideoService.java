package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.*;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoChannelResDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
public interface VideoService {
    ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser);
    ResponseDto updateVideo(VideoUpdateReqDto videoUpdateReqDto, UserDetails currentUser);
    List<VideoResponseDto> findAllVideos();
    VideoResponseDto getVideoById(Long videoId);

    ResponseDto findAllByIdChannel(Pageable pageable,String title,String status,String views, UserDetails currentUser);

    ResponseDto editFormVideoContent(String param, EditContentReqDto editContentReqDto, UserDetails currentUser);

    ResponseDto deleteVideoContent(DeleteContentReqDto deleteContentReqDto, UserDetails currentUser);
}
