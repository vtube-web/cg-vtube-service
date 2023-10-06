package com.cgvtube.cgvtubeservice.converter;

import com.cgvtube.cgvtubeservice.entiny.Video;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;

public interface VideoProcessing {
    Video convert(Video video, VideoUpdateReqDto videoUpdateReqDto);
}
