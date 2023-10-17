package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.LikedVideoConverter;
import com.cgvtube.cgvtubeservice.entity.UserLikedVideo;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.response.LikedVideoDTO;
import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.LikedVideoRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.LikedVideoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikedVideoServiceImpl implements LikedVideoService {

    private final LikedVideoRepository likedVideoRepository;
    private final UserRepository userRepository;
    private final LikedVideoConverter likedVideoConverter;
    private final VideoRepository videoRepository;

    @Override
    public ResponseDto getLikedVideos(UserDetails currentUser, Pageable pageableRequest) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Page<UserLikedVideo> likedVideos = likedVideoRepository.findByUser(user, pageableRequest);
        List<LikedVideoDTO> likedVideoDTOS = likedVideoConverter.apply(likedVideos);
        PageResponseDTO<LikedVideoDTO> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(likedVideoDTOS);
        pageResponseDTO.setHasNext(likedVideos.hasNext());
        pageResponseDTO.setPageSize(likedVideos.getSize());
        pageResponseDTO.setHasPrevious(likedVideos.hasPrevious());
        pageResponseDTO.setTotalPages(likedVideos.getTotalPages());
        pageResponseDTO.setCurrentPageNumber(likedVideos.getNumber());
        pageResponseDTO.setTotalElements(likedVideos.getTotalElements());
        ResponseDto responseDto = ResponseDto.builder().message("Successfully retrieved list of watched videos by userId: " + user.getId()).status("200").data(pageResponseDTO).build();
        return responseDto;
    }

    @Override
    public ResponseDto deleteLikedVideo(Long videoId, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Video video = videoRepository.findById(videoId).orElse(new Video());
        int deletedCount = likedVideoRepository.deleteByUserIdAndVideoId(user.getId(), videoId);
        ResponseDto responseDto;
        if (deletedCount == 0) {
            responseDto = ResponseDto.builder().message("No watched videos found for the user with userId: " + user.getId() + " & videoId: " + videoId).status("403").data(false).build();
        } else {
            decrementLikes(video);
            responseDto = ResponseDto.builder().message("Success delete videoId: " + videoId).status("200").data(true).build();
        }
        return responseDto;
    }

    @Override
    public ResponseDto addLikeVideo(Long videoId, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Boolean isAlreadyLiked = likedVideoRepository.existsByUserIdAndVideoId(user.getId(), videoId);
        ResponseDto responseDto;
        if (!isAlreadyLiked) {
            Video video = videoRepository.findById(videoId).orElse(new Video());
            UserLikedVideo userLikedVideo = new UserLikedVideo(user, video, LocalDateTime.now());
            likedVideoRepository.save(userLikedVideo);
            incrementLikes(video);
            responseDto = ResponseDto.builder().message("Successfully liked to the video").status("200").data(true).build();
        } else {
            responseDto = ResponseDto.builder().message("You are already Liked to this video").status("404").data(false).build();
        }
        return responseDto;
    }

    private void incrementLikes(Video video) {
        video.setLikes(video.getLikes() + 1);
        videoRepository.save(video);
    }


    private void decrementLikes(Video video) {
        video.setLikes(video.getLikes() - 1);
        videoRepository.save(video);
    }
}
