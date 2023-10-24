package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final VideoRepository videoRepository;
    private final VideoResponseConverter videoResponseConverter;
    @Override
    public ResponseDto findByKeyword(String search, Pageable pageable) {
        Page<Video> videoPage = videoRepository.findVideosByTitleContaining(search, pageable);
        Page<VideoResponseDto> videoResponseDtoPage = videoResponseConverter.convert(videoPage);
        return ResponseDto.builder()
                .status("200")
                .message("succeed search keyword")
                .data(videoResponseDtoPage)
                .build();
    }

    @Override
    public ResponseDto suggestByKeyword(String inputSearch, Pageable pageable) {
        Page<String> suggestionPage = videoRepository.findVideoTitlesContaining(inputSearch, pageable);
        return ResponseDto.builder()
                .status("200")
                .message("succeed suggestion keyword")
                .data(suggestionPage)
                .build();
    }
}
