package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.impl.LikedVideoConverter;
import com.cgvtube.cgvtubeservice.entity.UserLikedVideo;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.LikeOrDislikeReqDto;
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
        return ResponseDto.builder().message("Successfully retrieved list of watched videos by userId: " + user.getId()).status("200").data(pageResponseDTO).build();
    }

    @Override
    public ResponseDto deleteLikedVideo(Long videoId, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Video video = videoRepository.findById(videoId).orElse(new Video());
        int deletedCount = likedVideoRepository.deleteByUserIdAndVideoId(user.getId(), videoId);
        ResponseDto responseDto;
        if (deletedCount == 0) {
            responseDto = ResponseDto.builder().message("No watched videos found for the user with userId: " + user.getId() + " & videoId: " + videoId).status("404").data(false).build();
        } else {
            removeExistingLikeOrDislike(user, videoId, true);
            responseDto = ResponseDto.builder().message("Success delete videoId: " + videoId).status("200").data(true).build();
        }
        return responseDto;
    }

    @Override
    public ResponseDto addLikeOrDislikeVideo(LikeOrDislikeReqDto likeOrDislikeReqDto, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Long videoId = likeOrDislikeReqDto.getVideoId();
        Boolean likedStatus = likeOrDislikeReqDto.isLikedStatus();
        UserLikedVideo existingLikeStatus = likedVideoRepository.findByUserIdAndVideoId(user.getId(), videoId);
        ResponseDto responseDto;
        if (existingLikeStatus == null) {
            addNewLikeOrDislike(user, videoId, likedStatus);
            responseDto = ResponseDto.builder().message("Successfully liked/disliked the video").status("200").data(likedStatus).build();
        } else if (existingLikeStatus.isLikedStatus() == likedStatus) {
            removeExistingLikeOrDislike(user, videoId, likedStatus);
            responseDto = ResponseDto.builder().message("Removed like/dislike").status("200").data(0).build();
        } else {
            updateExistingLikeOrDislike(user, videoId, likedStatus);
            responseDto = ResponseDto.builder().message("Successfully updated like/dislike status").status("200").data(likedStatus).build();
        }
        return responseDto;
    }

    private void addNewLikeOrDislike(User user, Long videoId, Boolean likedStatus) {
        Video video = videoRepository.findById(videoId).orElse(new Video());
        UserLikedVideo userLikedVideo = new UserLikedVideo(user, video, LocalDateTime.now());
        userLikedVideo.setLikedStatus(likedStatus);
        likedVideoRepository.save(userLikedVideo);
        updateLikesDislikes(videoId, likedStatus, 1);
    }

    private void removeExistingLikeOrDislike(User user, Long videoId, Boolean likedStatus) {
        likedVideoRepository.deleteByUserIdAndVideoId(user.getId(), videoId);
        updateLikesDislikes(videoId, likedStatus, -1);
    }

    private void updateExistingLikeOrDislike(User user, Long videoId, Boolean likedStatus) {
        updateLikedStatus(user.getId(), videoId, likedStatus);
        changeLikesDislikes(videoId, likedStatus);
    }

    private void updateLikedStatus(Long userId, Long videoId, boolean likeStatus) {
        UserLikedVideo userLikedVideo = likedVideoRepository.findByUserIdAndVideoId(userId, videoId);
        userLikedVideo.setLikedStatus(likeStatus);
        likedVideoRepository.save(userLikedVideo);
    }

    private void updateLikesDislikes(Long videoId, boolean likeStatus, int increment) {
        Video video = videoRepository.findById(videoId).orElse(new Video());
        if (likeStatus) {
            video.setLikes(video.getLikes() + increment);
        } else {
            video.setDislikes(video.getDislikes() + increment);
        }
        videoRepository.save(video);
    }

    private void changeLikesDislikes(Long videoId, boolean likeStatus) {
        Video video = videoRepository.findById(videoId).orElse(new Video());
        if (likeStatus) {
            video.setLikes(video.getLikes() + 1);
            video.setDislikes(video.getDislikes() - 1);
        } else {
            video.setLikes(video.getLikes() - 1);
            video.setDislikes(video.getDislikes() + 1);
        }
        videoRepository.save(video);
    }
}
