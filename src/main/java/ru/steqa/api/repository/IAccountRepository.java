package ru.steqa.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.steqa.api.dto.AccountBalanceDto;
import ru.steqa.api.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll(Specification<Account> spec, Sort sort);
    List<Account> findAllByUserId(Long userId, Sort sort);
    Optional<Account> findByUserIdAndId(Long userId, Long id);

    @Query(value = """
                select a.id,
                       a.user_id,
                       a.name,
                       a.visible,
                       a.created_at,
                       coalesce(trans.balance, 0) +
                       coalesce(transfers_in.amount_in, 0) -
                       coalesce(transfers_out.amount_out, 0) as balance
                from accounts a
                         left join (select t.account_id,
                                           sum(case t.type
                                                   when 'INCOME' then t.amount
                                                   when 'EXPENSE' then -t.amount
                                               end) as balance
                                    from transactions t
                                    group by t.account_id) trans on trans.account_id = a.id
                         left join (select to_account_id as account_id,
                                           sum(amount)   as amount_in
                                    from transfers
                                    group by to_account_id) transfers_in on transfers_in.account_id = a.id
                         left join (select from_account_id as account_id,
                                           sum(amount)     as amount_out
                                    from transfers
                                    group by from_account_id) transfers_out on transfers_out.account_id = a.id
                where (:userId is null or a.user_id = :userId)
                  and (:visible is null or a.visible = :visible)
                order by a.created_at;
            """, nativeQuery = true)
    List<AccountBalanceDto> getAccountBalances(@Param("userId") Long userId, @Param("visible") Boolean visible);
}
