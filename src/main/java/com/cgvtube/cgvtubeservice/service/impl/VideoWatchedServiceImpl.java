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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoWatchedServiceImpl implements VideoWatchedService {

    private final VideoWatchedRepository videoWatchedRepository;
    private final WatchedVideoConverter watchedVideoConverter;
    private final UserRepository userRepository;

    @Override
    public ResponseDto findAllWatchedVideo(UserDetails currentUser, Pageable pageableRequest) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Page<UserWatchedVideo> watchedVideos = videoWatchedRepository.findByUser(user, pageableRequest);
        List<WatchedVideoDTO> watchedVideoDTOPage = watchedVideoConverter.apply(watchedVideos);

        PageResponseDTO<WatchedVideoDTO> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(watchedVideoDTOPage);
        pageResponseDTO.setPageSize(watchedVideos.getSize());
        pageResponseDTO.setTotalPages(watchedVideos.getTotalPages());
        pageResponseDTO.setHasNext(watchedVideos.hasNext());
        pageResponseDTO.setHasPrevious(watchedVideos.hasPrevious());
        pageResponseDTO.setTotalElements(watchedVideos.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(watchedVideos.getNumber());
        ResponseDto responseDto = ResponseDto.builder().message("Successfully retrieved list of watched videos by userId: " + user.getId()).status("200").data(pageResponseDTO).build();
        return responseDto;
    }

    @Override
    public ResponseDto deleteWatchedVideo(UserDetails currentUser, Long videoId) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        int deletedCount = videoWatchedRepository.deleteByUserIdAndVideoId(user.getId(), videoId);
        ResponseDto responseDto;
        if (deletedCount == 0) {
            responseDto = ResponseDto.builder().message("No watched videos found for the user with userId: " + user.getId() + " & videoId: " + videoId).status("403").data(false).build();
        } else {
            responseDto = ResponseDto.builder().message("Success delete videoId: " + videoId).status("200").data(true).build();
        }
        return responseDto;
    }

    @Override
    public ResponseDto deleteWatchedVideosByUserId(UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        int deletedCount = videoWatchedRepository.deleteByUserId(user.getId());
        ResponseDto responseDto;
        if (deletedCount == 0) {
            responseDto = ResponseDto.builder().message("No watched videos found for the user with userId: " + user.getId()).status("403").data(false).build();
        } else {
            responseDto = ResponseDto.builder().message("Delete all videos watched with userId: " + user.getId() + "success").status("200").data(true).build();
        }
        return responseDto;
    }
}
