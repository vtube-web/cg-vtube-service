package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponseDto {
    private Long id;
    private String title;
    private UserResponseDto userResponseDto;
    private List<VideoResponseDto> videoResponseDtoList;
}
