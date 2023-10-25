package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Playlist;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.PlaylistResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class PlaylistResponseConverter implements GeneralConverter<Playlist, PlaylistResponseDto> {
    private final UserResponseConverter userResponseConverter;
    private final UserRepository userRepository;
    private final VideoResponseConverter videoResponseConverter;

    @Override
    public PlaylistResponseDto convert(Playlist source) {
        PlaylistResponseDto target = new PlaylistResponseDto();
        BeanUtils.copyProperties(source, target);
        User user = userRepository.findById(source.getUser().getId())
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
        List<VideoResponseDto> videoResponseDtoList = videoResponseConverter.convert(source.getVideoList());
        target.setUserResponseDto(userResponseConverter.revert(user));
        target.setVideoResponseDtoList(videoResponseDtoList);
        return target;
    }

    @Override
    public Playlist revert(PlaylistResponseDto target) {
        return null;
    }

    @Override
    public List<PlaylistResponseDto> convert(List<Playlist> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Playlist> revert(List<PlaylistResponseDto> targets) {
        return null;
    }
}
