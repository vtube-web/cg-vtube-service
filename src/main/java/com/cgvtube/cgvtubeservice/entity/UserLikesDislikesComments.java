package com.cgvtube.cgvtubeservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_likes_dislikes_comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLikesDislikesComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean likes;

    private Boolean dislikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comments_id")
    private Comment comment;
}
