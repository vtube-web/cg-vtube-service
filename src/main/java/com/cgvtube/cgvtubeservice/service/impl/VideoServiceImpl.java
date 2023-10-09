package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.converter.impl.VideoResponseConverter;
import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.AddVideoResDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.VideoResponseDto;
import com.cgvtube.cgvtubeservice.repository.UserRepository;
import com.cgvtube.cgvtubeservice.repository.VideoRepository;
import com.cgvtube.cgvtubeservice.service.TagService;
import com.cgvtube.cgvtubeservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<VideoResponseDto> findAllVideos() {
        List<Video> videoList = videoRepository.findAll();
        return videoConverter.convert(videoList);
    }

    @Override
    public VideoResponseDto getVideoById(Long videoId) {
        Video video = videoRepository.findById(videoId).orElse(null);
        return videoConverter.convert(video);
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
}
