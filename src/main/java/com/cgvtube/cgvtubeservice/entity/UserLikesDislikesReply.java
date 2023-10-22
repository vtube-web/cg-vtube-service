package com.cgvtube.cgvtubeservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_likes_dislikes_replies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLikesDislikesReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Boolean likes;

    private Boolean dislikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "replies_id")
    private Reply reply;
}
