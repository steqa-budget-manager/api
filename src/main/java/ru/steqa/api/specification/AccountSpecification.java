package ru.steqa.api.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.steqa.api.model.Account;
import ru.steqa.api.scheme.account.AccountFilter;

public class AccountSpecification {
    public static Specification<Account> byFilter(Long userId, AccountFilter f) {
        return Specification
                .where(userIdEquals(userId))
                .and(visibleEquals(f.getVisible()));
    }

    private static Specification<Account> userIdEquals(Long userId) {
        return (root, cq, cb) ->
                userId == null ? null : cb.equal(root.get("userId"), userId);
    }

    private static Specification<Account> visibleEquals(Boolean visible) {
        return (root, cq, cb) ->
                visible == null ? null : cb.equal(root.get("visible"), visible);
    }
}
