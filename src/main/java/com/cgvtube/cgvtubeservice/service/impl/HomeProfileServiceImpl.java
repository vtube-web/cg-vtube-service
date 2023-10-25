package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.HomeProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class HomeProfileServiceImpl implements HomeProfileService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoResponseConverter videoConverter;
    @Override
    public ResponseDto getVideos(String userName, Pageable pageableRequest) {
        User user = userRepository.findByUserName(userName).orElse(new User());
        long userId = user.getId();
        Page<Video> videoPage = videoRepository.findAllByUserIdAndIsShorts(pageableRequest,userId,false);
        PageResponseDTO<VideoResponseDto> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(videoConverter.convert(videoPage.getContent()));
        pageResponseDTO.setPageSize(videoPage.getSize());
        pageResponseDTO.setTotalPages(videoPage.getTotalPages());
        pageResponseDTO.setHasNext(videoPage.hasNext());
        pageResponseDTO.setHasPrevious(videoPage.hasPrevious());
        pageResponseDTO.setTotalElements(videoPage.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(videoPage.getNumber());
        return ResponseDto.builder()
                .message("Successfully retrieved all videos according to userName channel")
                .status("200")
                .data(pageResponseDTO)
                .build();
    }



}
