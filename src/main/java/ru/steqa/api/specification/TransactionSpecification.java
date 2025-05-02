package ru.steqa.api.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.steqa.api.model.Transaction;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.transaction.TransactionFilter;

public class TransactionSpecification {
    public static Specification<Transaction> byFilter(Long userId, TransactionFilter f) {
        return Specification
                .where(userIdEquals(userId))
                .and(typeEquals(f.getType()));
    }

    private static Specification<Transaction> userIdEquals(Long userId) {
        return (root, cq, cb) ->
                userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    private static Specification<Transaction> typeEquals(TransactionType type) {
        return (root, cq, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }
}
