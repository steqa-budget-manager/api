package ru.steqa.api.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.steqa.api.model.TransactionTemplate;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.transaction.template.TransactionTemplateFilter;

public class TransactionTemplateSpecification {
    public static Specification<TransactionTemplate> byFilter(Long userId, TransactionTemplateFilter f) {
        return Specification
                .where(userIdEquals(userId))
                .and(typeEquals(f.getType()));
    }

    private static Specification<TransactionTemplate> userIdEquals(Long userId) {
        return (root, cq, cb) ->
                userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    private static Specification<TransactionTemplate> typeEquals(TransactionType type) {
        return (root, cq, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }
}
