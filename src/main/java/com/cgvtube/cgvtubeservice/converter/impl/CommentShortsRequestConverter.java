package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.CommentShorts;
import com.cgvtube.cgvtubeservice.payload.request.CommentShortsRequestDto;
import com.cgvtube.cgvtubeservice.repository.ShortsRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentShortsRequestConverter implements GeneralConverter<CommentShorts, CommentShortsRequestDto> {
    private final UserRepository userRepository;
    private final ShortsRepository shortsRepository;

    @Override
    public CommentShortsRequestDto convert(CommentShorts source) {
        return null;
    }

    @Override
    public CommentShorts revert(CommentShortsRequestDto target) {
        CommentShorts source = new CommentShorts();
        BeanUtils.copyProperties(target, source);
        source.setUser(userRepository.findById(target.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        source.setShorts(shortsRepository.findById(target.getShortsId())
                .orElseThrow(() -> new EntityNotFoundException("Video not found")));
        return source;
    }

    @Override
    public List<CommentShortsRequestDto> convert(List<CommentShorts> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<CommentShorts> revert(List<CommentShortsRequestDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
