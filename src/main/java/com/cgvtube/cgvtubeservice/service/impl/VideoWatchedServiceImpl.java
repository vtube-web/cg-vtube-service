package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.WatchedVideoConverter;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.WatchedVideo;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
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
    public PageResponseDTO<WatchedVideoDTO> findAllWatchedVideo(Long userId, Pageable pageableRequest) {
        User userOptional = userRepository.findById(userId).orElseThrow();
        Page<WatchedVideo> watchedVideos = videoWatchedRepository.findByUser(userOptional, pageableRequest);

        List<WatchedVideoDTO> watchedVideoDTOPage = watchedVideoConverter.apply(watchedVideos);

        PageResponseDTO<WatchedVideoDTO> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(watchedVideoDTOPage);
        pageResponseDTO.setPageSize(watchedVideos.getSize());
        pageResponseDTO.setTotalPages(watchedVideos.getTotalPages());
        pageResponseDTO.setHasNext(watchedVideos.hasNext());
        pageResponseDTO.setHasPrevious(watchedVideos.hasPrevious());
        pageResponseDTO.setTotalElements(watchedVideos.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(watchedVideos.getNumber());
        return pageResponseDTO;
    }

    @Override
    @Transactional
    public void deleteWatchedVideo(Long userId, Long videoId) throws Exception {
        int deletedCount = videoWatchedRepository.deleteByUserIdAndVideoId(userId, videoId);
        if (deletedCount == 0) {
            throw new Exception("No watched videos found for the user with userId: " + userId + " & videoId: " + videoId);
        }
    }

    @Override
    @Transactional
    public void deleteWatchedVideosByUserId(Long userId) throws Exception {
        int deletedCount = videoWatchedRepository.deleteByUserId(userId);
        if (deletedCount == 0) {
            throw new Exception("No watched videos found for the user with userId: " + userId);
        }
    }
}
