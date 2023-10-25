package com.cgvtube.cgvtubeservice.controller;


import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.ShortsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/shorts")
@RequiredArgsConstructor
public class ShortsController {

    private final ShortsService shortsService;

    @GetMapping
    public ResponseEntity<ResponseDto> findAllShorts(Pageable pageable) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(), 5);
        ResponseDto responseDto = shortsService.findAllShorts(pageableRequest);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
