package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoResponseConverter implements GeneralConverter<Video, VideoResponseDto> {
    private final TagConverter tagConverter;
    private final UserInfoConverter userInfoConverter;
    private final CommentService commentService;

    @Override
    public VideoResponseDto convert(Video source) {
        VideoResponseDto target = new VideoResponseDto();
        BeanUtils.copyProperties(source, target);
        target.setTagDtoList(tagConverter.convert(source.getTagSet()));
        target.setUserDto(userInfoConverter.revert(source.getUser()));
        target.setCommentDtoList(commentService.getListCommentDtoByVideo(source));
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
