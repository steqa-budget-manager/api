package ru.steqa.api.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @Column(name = "from_account_id", nullable = false, insertable = false, updatable = false)
    private Long fromAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @Column(name = "to_account_id", nullable = false, insertable = false, updatable = false)
    private Long toAccountId;

    @Builder
    public Transfer(Long amount, String description, LocalDate date, User user, Account fromAccount, Account toAccount) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.user = user;
        this.userId = user.getId();
        this.fromAccount = fromAccount;
        this.fromAccountId = fromAccount.getId();
        this.toAccount = toAccount;
        this.toAccountId = toAccount.getId();
    }
}
