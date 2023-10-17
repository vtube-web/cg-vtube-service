package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.*;
import com.cgvtube.cgvtubeservice.payload.response.*;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import com.cgvtube.cgvtubeservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private final Function<Page<Video>, PageResponseDTO<VideoChannelResDto>> pageResponseDTOFunction;

    public List<VideoResponseDto> findAllVideos() {
        List<Video> videoList = videoRepository.findAll();
        return videoConverter.convert(videoList);
    }

    @Override
    public VideoResponseDto getVideoById(Long videoId) {
        Video video = videoRepository.findById(videoId);
        return videoConverter.convert(video);
    }

    @Override
    public ResponseDto findAllByIdChannel(Pageable pageable, String title, String status, String views, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Sort sort = Sort.by(Sort.Direction.DESC, "createAt");
        Pageable pageableNew = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Video> videoPage;
        videoPage = getVideosIsPrivate(status, pageableNew, user);
        List<Video> videoList = videoPage.getContent();
        List<Video> videoListResult = new ArrayList<>();
        getVideosTitle(title, videoList, videoListResult);
        getVideosViews(views, videoListResult, videoList);

        Page<Video> videoPageResult = getVideosPageResult(videoListResult, videoList, title, status, views);

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
            Video video = videoRepository.findById(id);
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

    private static Page<Video> getVideosPageResult(List<Video> videoListResult, List<Video> videoList, String title, String status, String views) {
        Page<Video> videoPageResult;
        if (videoListResult.size() >= 0 && (!title.equals("") && title != null || !status.equals("") && status != null || !views.equals("") && views != null)) {
            videoPageResult = new PageImpl<>(videoListResult, PageRequest.of(0, 10), videoListResult.size());

        } else {
            videoPageResult = new PageImpl<>(videoList, PageRequest.of(0, 10), videoList.size());

        }
        return videoPageResult;
    }

    private static void getVideosViews(String views, List<Video> videoListResult) {

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

    private Page<Video> getVideosIsPrivate(String status, Pageable pageableNew, User user) {
        Page<Video> videoPage;
        if (!status.equals("") && status != null) {
            if (status.equals("private")) {
                videoPage = videoRepository.findAllByUserIdAndIsPrivate(pageableNew, user.getId(), true);
            } else {
                videoPage = videoRepository.findAllByUserIdAndIsPrivate(pageableNew, user.getId(), false);
            }
        } else {
            videoPage = videoRepository.findAllByUserId(pageableNew, user.getId());
        }
        return videoPage;
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
        Video video = videoRepository.findById(videoUpdateReqDto.getId());
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


}
