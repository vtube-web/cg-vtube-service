package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.*;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.DeleteContentReqDto;
import com.cgvtube.cgvtubeservice.payload.request.EditContentReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.*;
import com.cgvtube.cgvtubeservice.repository.SubscriptionRepository;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.repository.VideoWatchedRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import com.cgvtube.cgvtubeservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final Function<Page<Video>, PageResponseDTO<VideoChannelResDto>> pageResponseDTOFunction;
    private final VideoWatchedRepository videoWatchedRepository;
    private final Function<List<Video>,StatisticalVideoResDto> statisticalDTOFunction;
    private final SubscriptionRepository subscriptionRepository;

    public ResponseDto findAllVideos() {
        List<Video> videoList = videoRepository.findAllByIsShortsFalse();
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
    public ResponseDto findAllByIdChannel(Pageable pageable, String title, String status, String views, Boolean isShort, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageableNew = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Video> videoPage;
        videoPage = getVideosIsPrivate(status, pageableNew, user, isShort);
        List<Video> videoList = videoPage.getContent();
        List<Video> videoListResult = new ArrayList<>();
        getVideosTitle(title, videoList, videoListResult);
        getVideosViews(views, videoListResult, videoList);
        Page<Video> videoPageResult = getVideosPageResult(videoListResult, videoList, title, status, views, videoPage);
        ResponseDto responseDto = ResponseDto.builder()
                .message("List video channel").status("200")
                .data(pageResponseDTOFunction.apply(videoPageResult))
                .build();
        return responseDto;
    }

    private static void getVideosViews(String views, List<Video> videoListResult, List<Video> videoList) {
        if (!views.equals("") && views != null) {
            if (videoListResult.size() == 0) {
                videoListResult.addAll(videoList);
            }
            List<Video> videoListViewResult = new ArrayList<>();
            String[] operator = views.split("-");
            Long value = Long.parseLong(operator[1]);
            if (operator[0].equals("min")) {
                for (Video element : videoListResult) {
                    if (element.getViews() <= value) {
                        videoListViewResult.add(element);
                    }
                }
            } else {
                for (Video element : videoListResult) {
                    if (element.getViews() >= value) {
                        videoListViewResult.add(element);
                    }
                }
            }
            videoListResult.clear();
            videoListResult.addAll(videoListViewResult);
        }
    }

    @Override
    public ResponseDto editFormVideoContent(String param, EditContentReqDto editContentReqDto, UserDetails currentUser) {
        ResponseDto responseDto;
        if (editContentReqDto.getIdList() == null || editContentReqDto.getIdList().isEmpty()) {
            responseDto = ResponseDto.builder()
                    .message("List id video channel isEmpty").status("404")
                    .data(false)
                    .build();
            return responseDto;
        }
        List<Video> videoList = getVideosByIds(editContentReqDto.getIdList());
        switch (param) {
            case "title":
                for (Video element : videoList) {
                    element.setTitle(editContentReqDto.getValue());
                }
                break;
            case "description":
                for (Video element : videoList) {
                    element.setDescription(editContentReqDto.getValue());
                }
                break;
            case "hashtag":
                for (Video element : videoList) {
                    List<Tag> tagList = tagService.performAddAndCheckTag(List.of(editContentReqDto.getValue().split("#")));
                    element.setTagSet(tagList);
                }
                break;
            case "displayMode":
                for (Video element : videoList) {
                    boolean isPrivate = editContentReqDto.getValue().equals("private");
                    element.setIsPrivate(isPrivate);
                }
                break;
            default:
                break;
        }
        List<Video> videos = new ArrayList<>();
        for (Video element : videoList) {
            Video video = videoRepository.save(element);
            videos.add(video);
        }
        responseDto = ResponseDto.builder().message("Edit success").status("200").data(true).build();
        return responseDto;
    }

    private List<Video> getVideosByIds(List<Long> editContentReqDto) {
        List<Video> videoList = new ArrayList<>();
        for (Long id : editContentReqDto) {
            Video video = videoRepository.findById(id).orElse(new Video());
            videoList.add(video);
        }
        return videoList;
    }

    @Override
    public ResponseDto deleteVideoContent(DeleteContentReqDto deleteContentReqDto, UserDetails currentUser) {
        ResponseDto responseDto;
        if (deleteContentReqDto.getIdList() == null || deleteContentReqDto.getIdList().isEmpty()) {
            responseDto = ResponseDto.builder()
                    .message("List id video channel isEmpty").status("404")
                    .data(false)
                    .build();
            return responseDto;
        } else {
            List<Video> videoList = getVideosByIds(deleteContentReqDto.getIdList());
            for (Video element : videoList) {
                videoRepository.delete(element);
            }
            responseDto = ResponseDto.builder().message("Delete success").status("200").data(true).build();
        }
        return responseDto;
    }

    @Override
    public ResponseDto getStatisticalVideosDateNew(Long userId) {
        User user = userRepository.findById(userId).orElse(new User());
        if (user == null) {
            return ResponseDto
                    .builder()
                    .message("Authorization user token")
                    .status("401")
                    .data(false).build();
        }
        List<Video> video = videoRepository.findAllByUserId(user.getId());
        StatisticalVideoResDto statisticalVideoResDto = statisticalDTOFunction.apply(video);
        List<Subscription> subscriptions = subscriptionRepository.findAllByUserId(user.getId());
        statisticalVideoResDto.setSubscribe((long) subscriptions.size());
        return ResponseDto
                .builder()
                .message("get latest video success")
                .status("200")
                .data(statisticalVideoResDto).build();
    }

    private static Page<Video> getVideosPageResult(List<Video> videoListResult, List<Video> videoList, String title, String status, String views, Page<Video> videoPage) {
        Page<Video> videoPageResult;
        if (videoListResult.size() >= 0 && (!title.equals("") && title != null || !views.equals("") && views != null)) {
            videoPageResult = new PageImpl<>(videoListResult, PageRequest.of(videoPage.getNumber(), 10), videoPage.getTotalElements());
        } else {
            videoPageResult = new PageImpl<>(videoList, PageRequest.of(videoPage.getNumber(), 10), videoPage.getTotalElements());

        }
        return videoPageResult;
    }

    private static void getVideosTitle(String title, List<Video> videoList, List<Video> videoListResult) {
        if (!title.equals("") && title != null) {
            for (Video element : videoList) {
                if (element.getTitle().contains(title)) {
                    videoListResult.add(element);
                }
            }
        }
    }

    private Page<Video> getVideosIsPrivate(String status, Pageable pageableNew, User user, Boolean isShort) {
        Page<Video> videoPage;
        if (!status.equals("") && status != null) {
            if (status.equals("private")) {
                videoPage = videoRepository.findAllByUserIdAndIsPrivateAndIsShorts(pageableNew, user.getId(), true, isShort);
            } else {
                videoPage = videoRepository.findAllByUserIdAndIsPrivateAndIsShorts(pageableNew, user.getId(), false, isShort);
            }
        } else {
            videoPage = videoRepository.findAllByUserIdAndIsShorts(pageableNew, user.getId(), isShort);
        }
        return videoPage;
    }


    @Override
    public ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Video video = mapToVideo.apply(addVideoReqDto);
        video.setLikes(0L);
        video.setDislikes(0L);
        video.setViews(0L);
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