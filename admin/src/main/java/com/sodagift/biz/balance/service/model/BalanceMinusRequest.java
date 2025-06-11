//package com.sodagift.biz.balance.service.model;
//
//import com.sodagift.common.domain.Currency;
//import com.sodagift.biz.domain.balance.Ledger;
//import com.sodagift.biz.domain.balance.vo.Action;
//import java.math.BigDecimal;
//import javax.validation.constraints.NotNull;
//
//public record BalanceMinusRequest(
//        @NotNull
//        Long bulkId,
//        @NotNull
//        Long userId,
//        @NotNull
//        BigDecimal amount,
//        @NotNull
//        MetaData meta
//
//) {
//
//    public Ledger toLedger(long companyId, BigDecimal beforeAmount, BigDecimal afterAmount, Currency currency) {
//        return new Ledger(
//                meta.toAction(),
//                amount,
//                beforeAmount,
//                afterAmount,
//                currency,
//                null,
//                companyId,
//                null,
//                bulkId
//        );
//    }
//
//    public record MetaData(
//            @NotNull
//            MinusAction action
//    ) {
//
//        public enum MinusAction {
//            ORDER, ORDER_RESTORE
//        }
//
//        public Action toAction() {
//            return switch (action) {
//                case ORDER -> Action.ORDER;
//                case ORDER_RESTORE -> Action.ORDER_RESTORE;
//            };
//        }
//    }
//}
