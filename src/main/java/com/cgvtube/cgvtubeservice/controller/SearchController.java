package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    @GetMapping("/result")
    public ResponseEntity<ResponseDto> getResult(@RequestParam String search, @PageableDefault Pageable pageable){
        ResponseDto responseDto = searchService.findByKeyword(search, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/suggestion")
    public ResponseEntity<ResponseDto> getSuggestion(@RequestBody String inputSearch, @PageableDefault Pageable pageable){
        ResponseDto responseDto = searchService.suggestByKeyword(inputSearch, pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
