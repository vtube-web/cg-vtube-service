package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.ShortsResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Shorts;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.ShortsResponseDto;
import com.cgvtube.cgvtubeservice.repository.ShortsRepository;
import com.cgvtube.cgvtubeservice.service.ShortsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortsServiceImpl implements ShortsService {

    private final ShortsRepository shortsRepository;
    private final ShortsResponseConverter shortsConverter;

    @Override
    public ResponseDto findAllShorts(Pageable pageable) {
        Page<Shorts> shortsList = shortsRepository.findAll(pageable);
        PageResponseDTO<ShortsResponseDto> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(shortsConverter.convert(shortsList.getContent()));
        pageResponseDTO.setPageSize(shortsList.getSize());
        pageResponseDTO.setTotalPages(shortsList.getTotalPages());
        pageResponseDTO.setHasNext(shortsList.hasNext());
        pageResponseDTO.setHasPrevious(shortsList.hasPrevious());
        pageResponseDTO.setTotalElements(shortsList.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(shortsList.getNumber());
        return ResponseDto.builder()
                .message("success")
                .status("200")
                .data(pageResponseDTO)
                .build();
    }
}
