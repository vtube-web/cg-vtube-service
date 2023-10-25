package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import org.springframework.data.domain.Pageable;

public interface SearchService {
    ResponseDto findByKeyword(String search, Pageable pageable);

    ResponseDto suggestByKeyword(String inputSearch, Pageable pageable);
}
