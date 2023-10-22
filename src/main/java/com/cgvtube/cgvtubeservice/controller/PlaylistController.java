package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.PlaylistRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.PlaylistService;
import com.cgvtube.cgvtubeservice.service.VideoWatchedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlist")
public class PlaylistController {
    private final PlaylistService playlistService;
    private final VideoWatchedService videoWatchedService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getPlaylistByUserId(@PathVariable("userId") Long userId){
        ResponseDto responseDto = playlistService.getPlaylistsByUserId(userId);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> savePlaylist(@RequestBody PlaylistRequestDto playlistRequestDto){
        ResponseDto responseDto = playlistService.savePlaylist(playlistRequestDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping("/watched")
    public ResponseEntity<ResponseDto> getWatchedPlaylist(){
        ResponseDto responseDto = playlistService.getWatchedPlaylist();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
