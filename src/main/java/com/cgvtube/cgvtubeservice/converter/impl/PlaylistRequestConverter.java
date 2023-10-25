package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Playlist;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.PlaylistRequestDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaylistRequestConverter implements GeneralConverter<Playlist, PlaylistRequestDto> {
    private final UserRepository userRepository;
    private final VideoRequestConverter videoRequestConverter;
    @Override
    public PlaylistRequestDto convert(Playlist source) {
        return null;
    }

    @Override
    public Playlist revert(PlaylistRequestDto target) {
        Playlist source = new Playlist();
        BeanUtils.copyProperties(target,source);
        source.setUser(userRepository.findById(target.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        if(target.getVideoRequestDtoList() != null){
            List<Video> videoList = videoRequestConverter.revert(target.getVideoRequestDtoList());
            source.setVideoList(videoList);
        }
        return source;
    }

    @Override
    public List<PlaylistRequestDto> convert(List<Playlist> sources) {
        return null;
    }

    @Override
    public List<Playlist> revert(List<PlaylistRequestDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
