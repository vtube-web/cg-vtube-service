package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.DeleteContentReqDto;
import com.cgvtube.cgvtubeservice.payload.request.EditContentReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.VideoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping
    public ResponseEntity<ResponseDto> findAllVideos() {
        ResponseDto responseDto = videoService.findAllVideos();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<ResponseDto> getVideoByChannel(@PathVariable("videoId") Long videoId, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.getVideoById(videoId, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addVideo(@RequestBody AddVideoReqDto addVideoReqDto, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.addVideo(addVideoReqDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateVideo(@RequestBody VideoUpdateReqDto videoUpdateReqDto, HttpSession
            session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.updateVideo(videoUpdateReqDto, currentUser);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/subscribed")
    public ResponseEntity<ResponseDto> getAllVideosBySubscribedChannels(Pageable pageable, HttpSession session) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(), 18, Sort.by(Sort.Order.desc("createAt")));
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = videoService.findAllVideosBySubscribedChannels(currentUser, pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/content")
    public ResponseEntity<ResponseDto> findAllByIdChannel(@PageableDefault(value = 5) Pageable pageable, HttpSession session, @RequestParam String title,@RequestParam String status,@RequestParam String views ){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto  = videoService.findAllByIdChannel(pageable,title,status,views,currentUser);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PutMapping("/content/edit")
    public ResponseEntity<ResponseDto> editFormVideoContent(@RequestParam String param, @RequestBody EditContentReqDto editContentReqDto, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto  = videoService.editFormVideoContent(param,editContentReqDto,currentUser);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PutMapping("/content/delete")
    public ResponseEntity<ResponseDto> deleteVideoContent(@RequestBody DeleteContentReqDto deleteContentReqDto, HttpSession session){
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto  = videoService.deleteVideoContent(deleteContentReqDto,currentUser);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

}

