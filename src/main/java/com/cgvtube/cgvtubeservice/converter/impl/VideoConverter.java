package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.Converter;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoConverter implements Converter<Video, VideoResponseDto> {
    private final TagConverter tagConverter;
    private final UserResponseDtoConverter userResponseDtoConverter;
    private final CommentConverter commentConverter;

    @Override
    public VideoResponseDto convert(Video source) {
        VideoResponseDto target = new VideoResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setTagDtoList(tagConverter.convert(source.getTagSet()));
        target.setUserDto(userResponseDtoConverter.revert(source.getUser()));
        target.setCommentDtoList(commentConverter.convert(source.getCommentList()));
        return target;
    }

    @Override
    public Video revert(VideoResponseDto target) {
        Video source = new Video();
        BeanUtils.copyProperties(source, target);
        return source;
    }

    @Override
    public List<VideoResponseDto> convert(List<Video> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Video> revert(List<VideoResponseDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
