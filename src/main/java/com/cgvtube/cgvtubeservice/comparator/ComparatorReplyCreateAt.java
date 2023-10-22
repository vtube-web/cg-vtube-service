package com.cgvtube.cgvtubeservice.comparator;

import com.cgvtube.cgvtubeservice.payload.response.ReplyResponseDto;

import java.util.Comparator;

public class ComparatorReplyCreateAt implements Comparator<ReplyResponseDto> {
    @Override
    public int compare(ReplyResponseDto o1, ReplyResponseDto o2) {
        return o2.getCreateAt().compareTo(o1.getCreateAt());
    }
}
