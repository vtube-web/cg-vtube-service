package com.cgvtube.cgvtubeservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_watched_shorts")
public class WatchedShorts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "watched_at")
    private Timestamp watchedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "short_id",
            nullable = false,
            referencedColumnName = "id")
    private Shorts shorts;
}
