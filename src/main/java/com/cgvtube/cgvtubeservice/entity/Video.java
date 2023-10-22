package com.cgvtube.cgvtubeservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    @Column(name ="video_url")
    private String videoUrl;
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
    @Column(name = "is_private")
    private Boolean isPrivate;
    private Long views;
    private Long likes;
    private Long dislikes;
    private String duration;
    @Column(name = "is_shorts")
    private Boolean isShorts;
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "video")
    private List<Comment> commentList;

    @ManyToMany
    @JoinTable(
            name = "video_tag",
            joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Cascade(CascadeType.MERGE)
    private List<Tag> tagSet;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "video")
    private List<UserWatchedVideo> watchedUser;

    @OneToMany(mappedBy = "video")
    private List<UserLikedVideo> likedUser;
}
