package com.cgvtube.cgvtubeservice.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoUpdateReqDto {
    private long id;
    private String title;
    private String description;
    private String thumbnail ;
    private LocalDateTime release_date;
    private Boolean is_private;
    private List<String> hashtags;
}
