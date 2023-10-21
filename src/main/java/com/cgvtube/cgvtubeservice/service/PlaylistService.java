package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.PlaylistRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface PlaylistService {
    ResponseDto getWatchedPlaylistByUserId(Long userId);


    ResponseDto getPlaylistsByUserId(Long userId);

    ResponseDto savePlaylist(PlaylistRequestDto playlistRequestDto);
}
