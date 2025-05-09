package ru.steqa.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @Column(nullable = false)
    @ColumnDefault("true")
    @JsonIgnore
    private Boolean visible = true;

    @Column(name = "created_at", nullable = false)
    @ColumnDefault("now()")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Builder
    public Account(String name, User user) {
        this.name = name;
        this.user = user;
        this.userId = user.getId();
        this.visible = true;
        this.createdAt = ZonedDateTime.now();
    }
}
