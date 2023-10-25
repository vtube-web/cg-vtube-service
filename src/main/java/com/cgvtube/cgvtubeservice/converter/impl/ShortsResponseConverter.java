package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.ShortsResponseDto;
import com.cgvtube.cgvtubeservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShortsResponseConverter implements GeneralConverter<Video, ShortsResponseDto> {

    private final UserResponseConverter userResponseDtoConverter;
    private final CommentService commentService;
    @Override
    public ShortsResponseDto convert(Video source) {
        ShortsResponseDto target = new ShortsResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setUserDto(userResponseDtoConverter.revert(source.getUser()));
        target.setCommentShortsDtoList(commentService.getListCommentDtoByShorts(source));
        return target;
    }

    @Override
    public Video revert(ShortsResponseDto target) {
        Video source = new Video();
        BeanUtils.copyProperties(source, target);
        return source;
    }

    @Override
    public List<ShortsResponseDto> convert(List<Video> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Video> revert(List<ShortsResponseDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
