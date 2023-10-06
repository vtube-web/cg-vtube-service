package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.entiny.Video;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AddVideoReqDtoConverter implements Function<AddVideoReqDto,Video> {

    @Override
    public Video apply(AddVideoReqDto addVideoReqDto) {
        Video video = new Video();
        BeanUtils.copyProperties(addVideoReqDto,video);
        return video;
    }
}
