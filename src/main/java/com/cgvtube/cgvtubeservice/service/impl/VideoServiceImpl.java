package com.cgvtube.cgvtubeservice.service.impl;

import com.cgvtube.cgvtubeservice.converter.VideoProcessing;
import com.cgvtube.cgvtubeservice.converter.impl.VideoConverter;

import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.entity.User;
import com.cgvtube.cgvtubeservice.entity.Video;
import com.cgvtube.cgvtubeservice.payload.request.AddVideoReqDto;
import com.cgvtube.cgvtubeservice.payload.request.VideoUpdateReqDto;
import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.payload.response.AddVideoResDto;
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
    private VideoRepository videoRepository;
    private UserRepository userRepository;
    private TagService tagService;
    private Function<AddVideoReqDto, Video> mapToVideo;
    private Function<Video, AddVideoResDto> mapVideoToResponseDto;
    private VideoProcessing videoProcessing;
    private final VideoConverter videoConverter;
    public List<VideoResponseDto> getFirst40Videos() {
        List<Video> videoList = videoRepository.findFirst40Videos();
        return videoConverter.convert(videoList);
    }
    @Override
    public ResponseDto addVideo(AddVideoReqDto addVideoReqDto, UserDetails currentUser) {
        User user = userRepository.findByEmail(currentUser.getUsername()).orElse(new User());
        Video video = mapToVideo.apply(addVideoReqDto);
        video.setUser(user);
        Video videoResult = (Video) videoRepository.save(video);
        AddVideoResDto addVideoResDto = mapVideoToResponseDto.apply(videoResult);
        ResponseDto responseDto = ResponseDto.builder().message("created").status("200").data(addVideoResDto).build();
        return responseDto;
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
            responseDto = ResponseDto.builder().message("update").status("200").data(true).build();
        } else {
            responseDto = ResponseDto.builder().message("update error").status("403").data(false).build();
        }
        return responseDto;

    }


}
