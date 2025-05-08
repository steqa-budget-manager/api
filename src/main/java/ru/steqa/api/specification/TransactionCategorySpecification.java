package ru.steqa.api.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.steqa.api.model.TransactionCategory;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.transaction.category.TransactionCategoryFilter;

public class TransactionCategorySpecification {
    public static Specification<TransactionCategory> byFilter(Long userId, TransactionCategoryFilter f) {
        return Specification
                .where(userIdEquals(userId))
                .and(typeEquals(f.getType()))
                .and(visibleEquals(f.getVisible()));
    }

    private static Specification<TransactionCategory> userIdEquals(Long userId) {
        return (root, cq, cb) ->
                userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    private static Specification<TransactionCategory> typeEquals(TransactionType type) {
        return (root, cq, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }

    private static Specification<TransactionCategory> visibleEquals(Boolean visible) {
        return (root, cq, cb) ->
                visible == null ? null : cb.equal(root.get("visible"), visible);
    }
}
