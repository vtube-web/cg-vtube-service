package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.VideoRequestDto;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VideoRequestConverter implements GeneralConverter<Video, VideoRequestDto> {
    private final VideoRepository videoRepository;
    @Override
    public VideoRequestDto convert(Video source) {
        return null;
    }

    @Override
    public Video revert(VideoRequestDto target) {
        return videoRepository.findById(target.getId())
                .orElseThrow(()-> new EntityNotFoundException("Video not found with id"+target.getId()));
    }

    @Override
    public List<VideoRequestDto> convert(List<Video> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Video> revert(List<VideoRequestDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
