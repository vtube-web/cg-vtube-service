package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.VideoCommentChannelResDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VideoCommentChannelResDtoConverter implements Function<Video, VideoCommentChannelResDto> {
    @Override
    public VideoCommentChannelResDto apply(Video video) {
        VideoCommentChannelResDto channelResDto = new VideoCommentChannelResDto();
        BeanUtils.copyProperties(video,channelResDto);
        return channelResDto;
    }
}
