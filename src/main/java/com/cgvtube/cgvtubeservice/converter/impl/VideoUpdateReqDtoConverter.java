package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.entiny.Video;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VideoUpdateReqDtoConverter implements VideoProcessing {

    @Override
    public Video convert(Video video, VideoUpdateReqDto videoUpdateReqDto) {
        Video videoDefault = video;
        BeanUtils.copyProperties(videoUpdateReqDto,videoDefault);
        return videoDefault;
    }
}
