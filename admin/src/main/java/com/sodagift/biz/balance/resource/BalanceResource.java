//package com.sodagift.biz.balance.resource;
//
//import com.sodagift.biz.balance.service.BalanceService;
//import com.sodagift.biz.balance.service.model.BalanceMinusRequest;
//import com.sodagift.biz.balance.service.model.BalancePlusRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/balances")
//public class BalanceResource {
//
//    private final BalanceService balanceCommandService;
//
//    @PostMapping("/minus")
//    public void minus(@Validated @RequestBody BalanceMinusRequest request) {
//        balanceCommandService.minus(request);
//    }
//
//    @PostMapping("/plus")
//    public void plus(@Validated @RequestBody BalancePlusRequest request) {
//        balanceCommandService.plus(request);
//    }
//}
