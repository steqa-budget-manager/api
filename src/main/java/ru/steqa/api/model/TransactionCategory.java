package ru.steqa.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transaction_categories")
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TransactionType type;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @Column(nullable = false)
    @ColumnDefault("false")
    @JsonIgnore
    private Boolean visible = false;

    @Column(name = "created_at", nullable = false)
    @ColumnDefault("now()")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Builder
    public TransactionCategory(TransactionType type, String name, User user) {
        this.type = type;
        this.name = name;
        this.user = user;
        this.userId = user.getId();
        this.visible = true;
        this.createdAt = ZonedDateTime.now();
    }
}
