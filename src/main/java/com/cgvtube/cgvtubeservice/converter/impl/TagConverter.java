package com.cgvtube.cgvtubeservice.converter.impl;

import com.cgvtube.cgvtubeservice.converter.Converter;
import com.cgvtube.cgvtubeservice.entity.Tag;
import com.cgvtube.cgvtubeservice.payload.response.TagResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagConverter implements Converter<Tag, TagResponseDto> {
    @Override
    public TagResponseDto convert(Tag source) {
        TagResponseDto target = new TagResponseDto();
        BeanUtils.copyProperties(source,target);
        return target;
    }

    @Override
    public Tag revert(TagResponseDto target) {
        return null;
    }

    @Override
    public List<TagResponseDto> convert(List<Tag> sources) {
        return null;
    }

    @Override
    public List<Tag> revert(List<TagResponseDto> targets) {
        return null;
    }
}
