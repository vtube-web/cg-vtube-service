package com.cgvtube.cgvtubeservice.payload.response;

import lombok.Data;

@Data

public class AddVideoResDto {
    private long id;
    private String title;
    private String videoUrl;
}
