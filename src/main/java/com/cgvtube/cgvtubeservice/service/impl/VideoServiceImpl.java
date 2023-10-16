package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.UserWatchedVideo;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.AddVideoResDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.repository.VideoWatchedRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import com.cgvtube.cgvtubeservice.service.VideoService;
import com.cgvtube.cgvtubeservice.service.VideoWatchedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final Function<AddVideoReqDto, Video> mapToVideo;
    private final Function<Video, AddVideoResDto> mapVideoToResponseDto;
    private final VideoProcessing videoProcessing;
    private final VideoResponseConverter videoConverter;
    private final VideoWatchedService videoWatchedService;
    private final VideoWatchedRepository videoWatchedRepository;

    public ResponseDto findAllVideos() {
        List<Video> videoList = videoRepository.findAll();
        if (videoList.isEmpty()) {
            return ResponseDto.builder()
                    .message("Failed get video list")
                    .status("400")
                    .data(false)
                    .build();
        } else {
            return ResponseDto.builder()
                    .message("Succeed get video list")
                    .status("200")
                    .data(videoConverter.convert(videoList))
                    .build();
        }
    }

    @Override
    public ResponseDto getVideoById(Long videoId, UserDetails currentUser) {
        Video video = videoRepository.findById(videoId).orElse(null);
        if (currentUser != null) {
            User user = userRepository.findByEmail(currentUser.getUsername()).orElse(null);
            if (user != null) {
                LocalDateTime lastWatchedDate = getLastWatchedDate(user, videoId);
                if (lastWatchedDate != null && isSameDay(lastWatchedDate, LocalDateTime.now())) {
                    updateLastWatchedDate(user, videoId, LocalDateTime.now());
                    incrementViews(video);
                } else {
                    videoWatchedRepository.save(new UserWatchedVideo(video, user, LocalDateTime.now()));
                    incrementViews(video);
                }
            }
        } else {
            incrementViews(video);
        }
        return ResponseDto.builder()
                .message("Successfully retrieve video and record history")
                .status("200")
                .data(videoConverter.convert(video))
                .build();
    }

    @Override
    public ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Video video = mapToVideo.apply(addVideoReqDto);
        video.setUser(user);
        Video videoResult = (Video) videoRepository.save(video);
        AddVideoResDto addVideoResDto = mapVideoToResponseDto.apply(videoResult);
        return ResponseDto.builder()
                .message("created")
                .status("200")
                .data(addVideoResDto)
                .build();
    }

    @Override
    public ResponseDto updateVideo(VideoUpdateReqDto videoUpdateReqDto, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Video video = videoRepository.findById(videoUpdateReqDto.getId()).orElse(new Video());
        ResponseDto responseDto;
        if (Objects.equals(user.getId(), video.getUser().getId())) {
            List<Tag> tagList = tagService.performAddAndCheckTag(videoUpdateReqDto.getHashtags());
            Video videoConvert = videoProcessing.convert(video, videoUpdateReqDto);
            videoConvert.setTagSet(tagList);
            videoConvert.setCreateAt(LocalDateTime.now());
            videoRepository.save(videoConvert);
            responseDto = ResponseDto.builder()
                    .message("update")
                    .status("200")
                    .data(true)
                    .build();
        } else {
            responseDto = ResponseDto.builder()
                    .message("update error")
                    .status("403")
                    .data(false)
                    .build();
        }
        return responseDto;
    }

    private LocalDateTime getLastWatchedDate(User user, Long videoId) {
        Optional<UserWatchedVideo> lastWatchedVideo = videoWatchedRepository.findTopByUserIdAndVideoIdOrderByWatchedAtDesc(user.getId(), videoId);
        return lastWatchedVideo.map(UserWatchedVideo::getWatchedAt).orElse(null);
    }

    private void updateLastWatchedDate(User user, Long videoId, LocalDateTime newWatchedDate) {
        Optional<UserWatchedVideo> lastWatchedVideo = videoWatchedRepository.findTopByUserIdAndVideoIdOrderByWatchedAtDesc(user.getId(), videoId);
        lastWatchedVideo.ifPresent(video -> {
            video.setWatchedAt(newWatchedDate);
            videoWatchedRepository.save(video);
        });
    }

    private boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.toLocalDate().isEqual(date2.toLocalDate());
    }

    private void incrementViews(Video video) {
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }
}
