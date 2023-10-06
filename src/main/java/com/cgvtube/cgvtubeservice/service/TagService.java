package com.cgvtube.cgvtubeservice.service;

import com.cgvtube.cgvtubeservice.entiny.Tag;

import java.util.List;

public interface TagService {

    List<Tag> performAddAndCheckTag(List<String> hashtags);
}
