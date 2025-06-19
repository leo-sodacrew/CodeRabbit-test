package com.sodagift.biz.account;

import static com.sodagift.biz.domain.account.QAccount.account;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sodagift.biz.account.request.AccountCriteria;
import com.sodagift.biz.account.resource.response.AccountResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountQueryService {

    private final JPAQueryFactory queryFactory;

    public AccountQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Page<AccountResponse> findAll(AccountCriteria criteria, Pageable pageable) {
        Long totalCount = queryFactory
                .select(account.id.count())
                .from(account)
                .where(emailContains(criteria))
                .fetchOne();

        var results = queryFactory
                .select(Projections.constructor(AccountResponse.class,
                        account.id,
                        account.name,
                        account.email.value
                ))
                .from(account)
                .where(emailContains(criteria))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, totalCount);
    }

    private BooleanExpression emailContains(AccountCriteria criteria) {
        if (StringUtils.isEmpty(criteria.email())) {
            return null;
        }
        return account.email.value.containsIgnoreCase(criteria.email().trim());
    }
} 