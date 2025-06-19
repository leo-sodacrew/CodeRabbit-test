package com.sodagift.biz.kyc;

import static com.sodagift.biz.domain.account.QAccount.account;
import static com.sodagift.biz.domain.account.kyc.QKycSubmitHistory.kycSubmitHistory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sodagift.biz.domain.account.Account;
import com.sodagift.biz.kyc.request.KycCriteria;
import com.sodagift.biz.kyc.response.KycSubmitHistoryResponse;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class KycQueryService {

    private final JPAQueryFactory queryFactory;

    public KycQueryService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<KycSubmitHistoryResponse> findAll(KycCriteria criteria, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(KycSubmitHistoryResponse.class,
                        kycSubmitHistory.id,
                        kycSubmitHistory.account.id,
                        kycSubmitHistory.account.companyName,
                        kycSubmitHistory.account.type,
                        kycSubmitHistory.account.taxId,
                        kycSubmitHistory.purpose,
                        kycSubmitHistory.expectedSpendAmount,
                        kycSubmitHistory.spendType,
                        kycSubmitHistory.account.kycStatus,
                        kycSubmitHistory.fileNames,
                        kycSubmitHistory.createdAt,
                        kycSubmitHistory.updatedAt
                ))
                .from(kycSubmitHistory)
                .innerJoin(account).on(kycSubmitHistory.account.id.eq(account.id))
                .where(accountIdEq(criteria),
                        statusEq(criteria)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public long count(KycCriteria criteria) {
        return queryFactory
                .select(kycSubmitHistory.id.count())
                .from(kycSubmitHistory)
                .innerJoin(account).on(kycSubmitHistory.account.id.eq(account.id))
                .where(accountIdEq(criteria),
                        statusEq(criteria)
                )
                .fetchOne();
    }

    private BooleanExpression accountIdEq(KycCriteria criteria) {
        if (criteria.accountId() == null) {
            return null;
        }
        return kycSubmitHistory.account.id.eq(criteria.accountId());
    }

    private BooleanExpression statusEq(KycCriteria criteria) {
        if (StringUtils.isBlank(criteria.status())) {
            return null;
        }
        return kycSubmitHistory.account.kycStatus.eq(Account.KycStatus.valueOf(criteria.status()));
    }

} 