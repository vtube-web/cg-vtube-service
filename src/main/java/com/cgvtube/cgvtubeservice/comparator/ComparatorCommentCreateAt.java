package com.cgvtube.cgvtubeservice.comparator;

import com.cgvtube.cgvtubeservice.entity.Comment;

import java.util.Comparator;

public class ComparatorCommentCreateAt implements Comparator<Comment> {
    @Override
    public int compare(Comment o1, Comment o2) {
        return o2.getCreateAt().compareTo(o1.getCreateAt());
    }
}
