package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;

import java.util.List;

public interface VideoService {
    List<VideoResponseDto> getFirst40Videos();
}
