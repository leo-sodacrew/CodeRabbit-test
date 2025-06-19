package com.sodagift.biz.account.resource;

import com.sodagift.biz.account.request.AccountCriteria;
import com.sodagift.biz.account.resource.response.AccountResponse;
import com.sodagift.biz.account.service.AccountService;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biz/account")
public class AccountResource {

    private final AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Page<AccountResponse> findAll(AccountCriteria criteria, @PageableDefault(size = 25) Pageable pageable) {
        return accountService.findAll(criteria, pageable);
    }

    @GetMapping(params = "ids")
    public List<AccountResponse> getListByIds(@RequestParam Set<Long> ids) {
        return accountService.findByIds(ids);
    }
} 