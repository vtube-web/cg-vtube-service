package com.cgvtube.cgvtubeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscribe")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subscribe_at")
    private LocalDateTime subscribeAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "subscriber_id",
            nullable = false,
            referencedColumnName = "id")
    private User subscriber;
    
    public Subscription(User user, User subcriber, LocalDateTime now) {
        this.user = user;
        this.subscriber = subcriber;
        this.subscribeAt = now;
    }
}
