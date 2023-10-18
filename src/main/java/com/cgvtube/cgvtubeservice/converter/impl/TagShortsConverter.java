package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.GeneralConverter;
import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.payload.response.TagShortsResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagShortsConverter implements GeneralConverter<Tag, TagShortsResponseDto> {

    @Override
    public TagShortsResponseDto convert(Tag source) {
        TagShortsResponseDto target = new TagShortsResponseDto();
        BeanUtils.copyProperties(source,target);
        return target;
    }

    @Override
    public Tag revert(TagShortsResponseDto target) {
        return null;
    }

    @Override
    public List<TagShortsResponseDto> convert(List<Tag> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Tag> revert(List<TagShortsResponseDto> targets) {
        return null;
    }
}
