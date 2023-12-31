package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Comment;
import com.cgvtube.cgvtubeservice.payload.request.CommentRequestDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentRequestConverter implements GeneralConverter<Comment, CommentRequestDto> {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Override
    public CommentRequestDto convert(Comment source) {
        return null;
    }

    @Override
    public Comment revert(CommentRequestDto target) {
        Comment source = new Comment();
        BeanUtils.copyProperties(target, source);
        source.setUser(userRepository.findById(target.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        source.setVideo(videoRepository.findById(target.getVideoId())
                .orElseThrow(() -> new EntityNotFoundException("Video not found")));
        return source;
    }

    @Override
    public List<CommentRequestDto> convert(List<Comment> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Comment> revert(List<CommentRequestDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
