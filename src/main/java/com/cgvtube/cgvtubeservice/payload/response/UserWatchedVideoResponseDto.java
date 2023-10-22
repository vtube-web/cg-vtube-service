package com.cgvtube.cgvtubeservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWatchedVideoResponseDto {
    private Long id;
    private LocalDateTime watchedAt;
    private UserResponseDto userResponseDto;
    private VideoResponseDto videoResponseDto;
}
