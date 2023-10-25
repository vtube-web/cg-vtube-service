package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.PlaylistRequestConverter;
import com.cgvtube.cgvtubeservice.converter.impl.PlaylistResponseConverter;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Playlist;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserWatchedVideo;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.PlaylistRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.PlaylistResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.repository.PlaylistRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.repository.VideoWatchedRepository;
import com.cgvtube.cgvtubeservice.service.PlaylistService;
import com.cgvtube.cgvtubeservice.service.UserService;
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
    private final VideoWatchedRepository videoWatchedRepository;
    private final VideoResponseConverter videoResponseConverter;
    private final PlaylistRepository playlistRepository;
    private final PlaylistRequestConverter playlistRequestConverter;
    private final PlaylistResponseConverter playlistResponseConverter;
    private final UserService userService;

    @Override
    public ResponseDto getWatchedPlaylist() {
        User user = userRepository.findById(userService.getCurrentUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<UserWatchedVideo> watchedVideos = videoWatchedRepository.findAllByUser(user);
        List<Video> videoList = watchedVideos.stream().map(UserWatchedVideo::getVideo).toList();
        List<VideoResponseDto> videoResponseDtoList = videoResponseConverter.convert(videoList);
        PlaylistResponseDto playlistResponseDto = PlaylistResponseDto.builder()
                .title("Mixed Watched Playlist")
                .videoResponseDtoList(videoResponseDtoList)
                .build();
        return ResponseDto.builder()
                .status("200")
                .message("Succeed get Watched Playlist")
                .data(playlistResponseDto)
                .build();
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
