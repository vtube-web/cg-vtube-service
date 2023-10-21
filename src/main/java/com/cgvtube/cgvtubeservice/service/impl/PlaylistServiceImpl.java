package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.PlaylistRequestConverter;
import com.cgvtube.cgvtubeservice.converter.impl.PlaylistResponseConverter;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Playlist;
import com.cgvtube.cgvtubeservice.payload.request.PlaylistRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.PlaylistResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.PlaylistRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.PlaylistService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistServiceImpl implements PlaylistService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoResponseConverter videoResponseConverter;
    private final PlaylistRepository playlistRepository;
    private final PlaylistRequestConverter playlistRequestConverter;
    private final PlaylistResponseConverter playlistResponseConverter;

    @Override
    public ResponseDto getWatchedPlaylistByUserId(Long userId) {
        return null;
    }

    @Override
    public ResponseDto getPlaylistsByUserId(Long userId) {
        List<Playlist> playlistList = playlistRepository.findAllByUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        List<PlaylistResponseDto> playlistResponseDtoList = playlistResponseConverter.convert(playlistList);
        return ResponseDto.builder()
                .status("200")
                .message("Succeed get Playlist by user ID")
                .data(playlistResponseDtoList)
                .build();
    }

    @Override
    public ResponseDto savePlaylist(PlaylistRequestDto playlistRequestDto) {
        Playlist playlist = playlistRequestConverter.revert(playlistRequestDto);
        playlistRepository.save(playlist);
        return ResponseDto.builder()
                .status("200")
                .message("Succeed save playlist")
                .data(true)
                .build();
    }

}
