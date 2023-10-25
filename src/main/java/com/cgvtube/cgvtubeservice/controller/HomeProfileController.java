package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.HomeProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/homeProfile")
public class HomeProfileController {
    private final HomeProfileService homeProfileService;

    @GetMapping("/videos")
    public ResponseEntity<ResponseDto> getHomeProfileVideos(Pageable pageable, @RequestParam("userName") String userName, @RequestParam("type") String type) {
        Pageable pageableRequest;
        if ("Oldest".equals(type)) {
            pageableRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "createAt");
        } else if ("Popular".equals(type)) {
            pageableRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "views");
        } else {
            pageableRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createAt");

        }
        ResponseDto responseDto = homeProfileService.getVideos(userName, pageableRequest);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
