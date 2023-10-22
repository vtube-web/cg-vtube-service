package com.cgvtube.cgvtubeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "replies")
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long likes;
    private Long dislikes;
    private String content;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            nullable = false,
            referencedColumnName = "id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "reply")
    private List<UserLikesDislikesReply> userLikesDislikes;
}