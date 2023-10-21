package com.cgvtube.cgvtubeservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "avatar")
    private String avatar;

    @Column(name ="banner")
    private String banner;

    @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Video> videoList;

    @ManyToMany(targetEntity = Video.class)
    @JoinTable(name = "user_liked_video",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"))
    private List<Video> likedVideo;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    private List<Reply> replyList;

    @OneToMany(mappedBy = "user")
    private List<UserWatchedVideo> watchedVideos;

    @OneToMany(mappedBy = "user")
    private List<UserLikedVideo> likedVideos;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "user")
    private List<UserLikesDislikesComments> likesDislikesComments;

    @OneToMany(mappedBy = "user")
    private List<UserLikesDislikesReply> likesDislikesReplies;

}
