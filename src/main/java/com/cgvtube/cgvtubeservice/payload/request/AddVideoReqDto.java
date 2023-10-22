package com.cgvtube.cgvtubeservice.payload.request;

import lombok.Data;

@Data
public class AddVideoReqDto {
    private String title;
    private String videoUrl;
    private String duration;
}
