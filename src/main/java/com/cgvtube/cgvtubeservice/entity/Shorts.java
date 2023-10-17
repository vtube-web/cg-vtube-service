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
@Table(name = "shorts")
public class Shorts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name ="shorts_url")
    private String shortsUrl;
    @Column(name = "is_private")
    private Boolean isPrivate;
    private Long views;
    private Long likes;
    private Long dislikes;
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "shorts")
    private List<CommentShorts> commentList;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "shorts")
    private List<WatchedShorts> watchedShorts;

    @OneToMany(mappedBy = "shorts")
    private List<LikedShorts> likedShorts;

}
