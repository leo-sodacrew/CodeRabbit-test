//package com.sodagift.biz.balance.service.model;
//
//import com.sodagift.common.domain.Currency;
//import com.sodagift.biz.domain.balance.Balance;
//import com.sodagift.biz.domain.balance.Ledger;
//import com.sodagift.biz.domain.balance.vo.Action;
//import com.sodagift.biz.domain.payment.Payment;
//import java.math.BigDecimal;
//import java.util.Optional;
//import javax.validation.constraints.NotNull;
//
//public record BalancePlusRequest(
//        @NotNull
//        Long userId,
//        BigDecimal amount,
//        @NotNull
//        MetaData meta
//) {
//
//    public Payment toPaymentWithStripe(Balance balance, BigDecimal amount, BigDecimal fee) {
//        return new Payment(userId, amount, balance.currency(), balance.accountGroup().pgVendor(), meta.invoiceId(), fee);
//    }
//
//    public Payment toPayment(Balance balance) {
//        return new Payment(userId, amount, balance.currency(), balance.accountGroup().pgVendor(),
//                meta.ibkTransactionNo(), BigDecimal.ZERO);
//    }
//
//    public Ledger toLedger(Long companyId, BigDecimal amount, BigDecimal beforeAmount, BigDecimal afterAmount, Currency currency, Long paymentId) {
//        return new Ledger(
//                meta.toAction(),
//                amount,
//                beforeAmount,
//                afterAmount,
//                currency,
//                null,
//                companyId,
//                paymentId,
//                null
//        );
//    }
//
//    public Optional<String> maybeInvoiceId() {
//        return Optional.ofNullable(meta.invoiceId());
//    }
//
//    public Optional<String> maybeIbkTransactionNo() {
//        return Optional.ofNullable(meta.ibkTransactionNo());
//    }
//
//    public record MetaData(
//            @NotNull
//            PlusAction action,
//            String invoiceId,
//            String ibkTransactionNo
//    ) {
//
//        public Action toAction() {
//            return switch (action) {
//                case CHARGE -> Action.CHARGE;
//                case SETTLE -> Action.SETTLE;
//            };
//        }
//
//        public enum PlusAction {
//            CHARGE, SETTLE
//        }
//    }
//}
