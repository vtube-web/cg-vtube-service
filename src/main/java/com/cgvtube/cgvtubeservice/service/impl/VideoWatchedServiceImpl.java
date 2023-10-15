package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.WatchedVideoConverter;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserWatchedVideo;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.WatchedVideoDTO;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoWatchedRepository;
import com.cgvtube.cgvtubeservice.service.VideoWatchedService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoWatchedServiceImpl implements VideoWatchedService {

    private final VideoWatchedRepository videoWatchedRepository;
    private final WatchedVideoConverter watchedVideoConverter;
    private final UserRepository userRepository;

    @Override
    public ResponseDto findAllWatchedVideo(Long userId, Pageable pageableRequest) {
        User userOptional = userRepository.findById(userId).orElseThrow();
        Page<UserWatchedVideo> watchedVideos = videoWatchedRepository.findByUser(userOptional, pageableRequest);

        List<WatchedVideoDTO> watchedVideoDTOPage = watchedVideoConverter.apply(watchedVideos);

        PageResponseDTO<WatchedVideoDTO> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(watchedVideoDTOPage);
        pageResponseDTO.setPageSize(watchedVideos.getSize());
        pageResponseDTO.setTotalPages(watchedVideos.getTotalPages());
        pageResponseDTO.setHasNext(watchedVideos.hasNext());
        pageResponseDTO.setHasPrevious(watchedVideos.hasPrevious());
        pageResponseDTO.setTotalElements(watchedVideos.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(watchedVideos.getNumber());
        ResponseDto responseDto = ResponseDto.builder().message("list video watched").status("200").data(pageResponseDTO).build();
        return responseDto;
    }

    @Override
    @Transactional
    public ResponseDto deleteWatchedVideo(Long userId, Long videoId) throws Exception {
        int deletedCount = videoWatchedRepository.deleteByUserIdAndVideoId(userId, videoId);
        ResponseDto responseDto;
        if (deletedCount == 0) {
            responseDto = ResponseDto.builder().message("No watched videos found for the user with userId: " + userId + " & videoId: " + videoId).status("403").data(false).build();
        } else {
            responseDto = ResponseDto.builder().message("Delete videoId: " + videoId + "success").status("200").data(true).build();
        }
        return responseDto;
    }

    @Override
    @Transactional
    public ResponseDto deleteWatchedVideosByUserId(Long userId) throws Exception {
        int deletedCount = videoWatchedRepository.deleteByUserId(userId);
        ResponseDto responseDto;
        if (deletedCount == 0) {
            responseDto = ResponseDto.builder().message("No watched videos found for the user with userId: " + userId).status("403").data(false).build();
        } else {
            responseDto = ResponseDto.builder().message("Delete all videos watched with userId: " + userId + "success").status("200").data(true).build();
        }
        return responseDto;
    }
}
