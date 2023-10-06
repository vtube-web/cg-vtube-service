package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entiny.Video;
import com.cgvtube.cgvtubeservice.payload.response.AddVideoResDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddVideoResDtoConverter implements Function<Video, AddVideoResDto> {
    @Override
    public AddVideoResDto apply(Video video) {
        AddVideoResDto addVideoResDto = new AddVideoResDto();
        BeanUtils.copyProperties(video,addVideoResDto);
        return addVideoResDto;
    }
}
