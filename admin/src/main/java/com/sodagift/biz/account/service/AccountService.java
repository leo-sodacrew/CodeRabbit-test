package com.sodagift.biz.account.service;

import com.sodagift.biz.account.AccountQueryService;
import com.sodagift.biz.account.request.AccountCriteria;
import com.sodagift.biz.account.resource.response.AccountResponse;
import com.sodagift.biz.domain.account.AccountRepository;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountQueryService accountQueryService;
    private final AccountRepository accountRepository;

    public AccountService(AccountQueryService accountQueryService, AccountRepository accountRepository) {
        this.accountQueryService = accountQueryService;
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Page<AccountResponse> findAll(AccountCriteria criteria, Pageable pageable) {
        return accountQueryService.findAll(criteria, pageable);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> findByIds(Set<Long> ids) {
        return accountRepository.findAllById(ids).stream()
                .map(AccountResponse::from)
                .toList();
    }
} 