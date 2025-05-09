package ru.steqa.api.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.steqa.api.model.TransactionRegular;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.transaction.regular.TransactionRegularFilter;

public class TransactionRegularSpecification {
    public static Specification<TransactionRegular> byFilter(Long userId, TransactionRegularFilter f) {
        return Specification
                .where(userIdEquals(userId))
                .and(typeEquals(f.getType()));
    }

    private static Specification<TransactionRegular> userIdEquals(Long userId) {
        return (root, cq, cb) ->
                userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    private static Specification<TransactionRegular> typeEquals(TransactionType type) {
        return (root, cq, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }
}
