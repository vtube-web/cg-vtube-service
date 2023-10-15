package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.LikedVideoConverter;
import com.cgvtube.cgvtubeservice.entity.userLikedVideo;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.payload.response.LikedVideoDTO;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.LikedVideoRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.service.LikedVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedVideoServiceImpl implements LikedVideoService {

    private final LikedVideoRepository likedVideoRepository;
    private final UserRepository userRepository;
    private final LikedVideoConverter likedVideoConverter;
    @Override
    public ResponseDto getLikedVideos(Long userId, Pageable pageableRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        Page<userLikedVideo> likedVideos = likedVideoRepository.findByUser(user, pageableRequest);
        List<LikedVideoDTO> likedVideoDTOS = likedVideoConverter.apply(likedVideos);
        PageResponseDTO<LikedVideoDTO> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(likedVideoDTOS);
        pageResponseDTO.setPageSize(likedVideos.getSize());
        pageResponseDTO.setTotalPages(likedVideos.getTotalPages());
        pageResponseDTO.setHasNext(likedVideos.hasNext());
        pageResponseDTO.setHasPrevious(likedVideos.hasPrevious());
        pageResponseDTO.setTotalElements(likedVideos.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(likedVideos.getNumber());
        ResponseDto responseDto = ResponseDto.builder().message("list liked video").status("200").data(pageResponseDTO).build();
        return responseDto;
    }
}
