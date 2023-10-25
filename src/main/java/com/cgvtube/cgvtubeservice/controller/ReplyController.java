package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.ContentReplyReqDto;
import com.cgvtube.cgvtubeservice.payload.request.ReplyRequestDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/{commentId}/reply")
    public ResponseEntity<ResponseDto> saveReply(@PathVariable("commentId") Long commentId,
                                                 @RequestBody  ReplyRequestDto replyRequestDto) {
        ResponseDto responseDto = replyService.save(commentId, replyRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/reply")
    public ResponseEntity<ResponseDto> editContentOfReplyByUser(@RequestBody ContentReplyReqDto contentReplyReqDto){
        ResponseDto responseDto = replyService.editContentOfReplyByUser(contentReplyReqDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
    @DeleteMapping("/reply/{id}")
    public ResponseEntity<ResponseDto> deleteContentOfReplyByUser(@PathVariable Long id){
        ResponseDto responseDto = replyService.deleteContentOfReplyByUser(id);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}
