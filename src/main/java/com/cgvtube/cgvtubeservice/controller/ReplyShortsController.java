package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.ReplyShortsRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyShortsController {

    private final ReplyService replyService;

    @PostMapping("/{commentShortsId}/replyShort")
    public ResponseEntity<ResponseDto> saveReply(@PathVariable("commentShortsId") Long commentShortsId,
                                                 @RequestBody ReplyShortsRequestDto replyShortsRequestDto) {
        ResponseDto responseDto = replyService.saveShorts(commentShortsId, replyShortsRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
