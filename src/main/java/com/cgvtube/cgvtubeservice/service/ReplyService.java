package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.request.ReplyRequestDto;
import com.cgvtube.cgvtubeservice.payload.request.ReplyShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;

public interface ReplyService {

    ResponseDto save (Long commentId, ReplyRequestDto replyRequestDto);

    ResponseDto save (Long shortsId, ReplyShortsRequestDto replyShortsRequestDto);
}
