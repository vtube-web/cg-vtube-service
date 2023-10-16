package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.*;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.*;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.repository.VideoWatchedRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import com.cgvtube.cgvtubeservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final VideoWatchedRepository videoWatchedRepository;

    public List<VideoResponseDto> findAllVideos() {
        List<Video> videoList = videoRepository.findAll();
        return videoConverter.convert(videoList);
    }

    @Override
    public ResponseDto getVideoById(Long videoId, UserDetails currentUser) {
        Video video = videoRepository.findById(videoId).orElse(new Video());
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
    public ResponseDto findAllVideosBySubscribedChannels(UserDetails currentUser, Pageable pageableRequest) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        List<Subscription> subscriptions = user.getSubscriptions();
        List<Long> channelIds = subscriptions.stream().map(subscription -> subscription.getSubscriber().getId()).collect(Collectors.toList());
        Page<Video> videoPage = videoRepository.findVideosByChannelIds(channelIds, pageableRequest);
        PageResponseDTO<VideoResponseDto> pageResponseDTO = new PageResponseDTO<>();
        pageResponseDTO.setContent(videoConverter.convert(videoPage.getContent()));
        pageResponseDTO.setPageSize(videoPage.getSize());
        pageResponseDTO.setTotalPages(videoPage.getTotalPages());
        pageResponseDTO.setHasNext(videoPage.hasNext());
        pageResponseDTO.setHasPrevious(videoPage.hasPrevious());
        pageResponseDTO.setTotalElements(videoPage.getTotalElements());
        pageResponseDTO.setCurrentPageNumber(videoPage.getNumber());
        return ResponseDto.builder()
                .message("Successfully retrieved all videos according to the subscription channel")
                .status("200")
                .data(pageResponseDTO)
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
        if (user.getId() == video.getUser().getId()) {
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
