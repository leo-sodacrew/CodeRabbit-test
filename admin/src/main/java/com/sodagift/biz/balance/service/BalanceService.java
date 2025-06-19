//package com.sodagift.biz.balance.service;
//
//import com.sodagift.biz.balance.service.exception.AccountBalanceInsufficientException;
//import com.sodagift.biz.balance.service.model.BalanceMinusRequest;
//import com.sodagift.biz.balance.service.model.BalancePlusRequest;
//import com.sodagift.biz.domain.auth.Account;
//import com.sodagift.biz.domain.auth.AccountGroup;
//import com.sodagift.biz.domain.auth.AccountRepository;
//import com.sodagift.biz.domain.auth.CompanyRepository;
//import com.sodagift.biz.domain.balance.Balance;
//import com.sodagift.biz.domain.balance.BalanceRepository;
//import com.sodagift.biz.domain.balance.LedgerRepository;
//import com.sodagift.biz.domain.payment.Payment;
//import com.sodagift.biz.domain.payment.PaymentRepository;
//import com.sodagift.biz.domain.payment.vo.PgVendor;
//import com.sodagift.biz.thirdparty.stripe.StripePaymentAdaptar;
//import com.sodagift.biz.thirdparty.stripe.StripePaymentAdaptar.StripePaymentResponse;
//import java.math.BigDecimal;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class BalanceService {
//
//    private final CompanyRepository companyRepository;
//    private final AccountRepository accountRepository;
//    private final BalanceRepository balanceRepository;
//
//    private final LedgerRepository ledgerRepository;
//
//    private final StripePaymentAdaptar stripePaymentAdaptar;
//    private final PaymentRepository paymentRepository;
//
//    public void minus(BalanceMinusRequest request) {
//        var account = findAccountById(request.userId());
//        var company = findCompanyById(account);
//
//        validateBulkOrderId(request.bulkId(), company.id());
//        minusBalanceAndSave(request, company);
//    }
//
//    public void plus(BalancePlusRequest request) {
//        var account = findAccountById(request.userId());
//        var company = findCompanyById(account);
//
//        var balance = findBalanceByCompanyId(company.id());
//        var payment = paymentRepository.save(payment(request, company, balance));
//
//        plusBalanceAndSave(request, balance, payment, company);
//    }
//
//    private void plusBalanceAndSave(BalancePlusRequest request, Balance balance, Payment payment, AccountGroup accountGroup) {
//        var beforeAmount = balance.amount();
//        var afterAmount = balance.plus(payment.amount()).amount();
//
//        validateAfterAmount(accountGroup, afterAmount);
//        ledgerRepository.save(request.toLedger(accountGroup.id(), payment.amount(), beforeAmount, afterAmount, balance.currency(), payment.id()));
//    }
//
//    private void validateAfterAmount(AccountGroup accountGroup, BigDecimal afterAmount) {
//        if (!accountGroup.isPrepaid() && afterAmount.compareTo(BigDecimal.ZERO) > 0) {
//            throw new IllegalArgumentException("postpaid account balance must be less than or equal to 0");
//        }
//    }
//
//    private Payment payment(BalancePlusRequest request, AccountGroup accountGroup, Balance balance) {
//        if (accountGroup.pgVendor() == PgVendor.STRIPE) {
//            var stripePayment = stripePayment(request);
//            validateExistsInvoiceId(request);
//
//            return request.toPaymentWithStripe(balance, stripePayment.amountValue(), stripePayment.feeValue());
//        }
//
//        validateIbkTransactionNo(request);
//
//        return request.toPayment(balance);
//    }
//
//    private void minusBalanceAndSave(BalanceMinusRequest request, AccountGroup accountGroup) {
//        var balance = findBalanceByCompanyId(accountGroup.id());
//
//        var beforeAmount = balance.amount();
//        var afterAmount = minusBalance(request, balance);
//
//        ledgerRepository.save(request.toLedger(accountGroup.id(), beforeAmount, afterAmount, balance.currency()));
//    }
//
//    private BigDecimal minusBalance(BalanceMinusRequest request, Balance balance) {
//        try {
//            return balance.minus(request.amount()).amount();
//        } catch (IllegalStateException e) {
//            throw new AccountBalanceInsufficientException(request.userId());
//        }
//    }
//
//    private Balance findBalanceByCompanyId(Long companyId) {
//        return balanceRepository.findByCompanyId(companyId)
//                .orElseThrow(() -> new IllegalStateException("Not found companyId: " + companyId));
//    }
//
//    private void validateBulkOrderId(long bulkId, long companyId) {
////        var isBulkOrder = bulkOrderRepository.existsByIdAndCompanyId(bulkId, companyId);
////        if (!isBulkOrder) {
////            throw new IllegalArgumentException("Not found bulkId: " + bulkId);
////        }
//    }
//
//    private Account findAccountById(long accountId) {
//        return accountRepository.findById(accountId)
//                .orElseThrow(() -> new IllegalStateException("Not found userId: " + accountId));
//    }
//
//    private AccountGroup findCompanyById(Account account) {
//        return companyRepository.findById(account.companyId())
//                .orElseThrow();
//    }
//
//    private StripePaymentResponse stripePayment(BalancePlusRequest request) {
//        try {
//            return request.maybeInvoiceId().map(stripePaymentAdaptar::find)
//                    .orElseThrow();
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Not found invoiceId: " + request.meta().invoiceId());
//        }
//    }
//
//    private void validateExistsInvoiceId(BalancePlusRequest request) {
//        request.maybeInvoiceId().map(paymentRepository::existsByTransactionNo)
//                .ifPresent(exists -> {
//                    if (exists) {
//                        throw new IllegalArgumentException("Already exists invoiceId: " + request.meta().invoiceId());
//                    }
//                });
//    }
//
//    private void validateIbkTransactionNo(BalancePlusRequest request) {
//        if (request.maybeIbkTransactionNo().isEmpty()) {
//            throw new IllegalArgumentException("Not found ibkTransactionNo: " + request.meta().ibkTransactionNo());
//        }
//        var ibkTransactionNo = request.maybeIbkTransactionNo().get();
//        if (paymentRepository.existsByTransactionNo(ibkTransactionNo)) {
//            throw new IllegalArgumentException("Already exists ibkTransactionNo: " + ibkTransactionNo);
//        }
//    }
//}
