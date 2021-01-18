package com.demo.ibm.controller;

import com.demo.ibm.model.ResponseData;
import com.demo.ibm.model.balance.Balance;
import com.demo.ibm.model.transaction.Transaction;
import com.demo.ibm.model.transaction.TransactionCritea;
import com.demo.ibm.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private  AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping(value = "/balance/{id}")
    @ResponseBody
    public ResponseData<Balance> getAccountBalance(@PathVariable("id") String getAccountNumber) {
        return accountService.getAccountBalance(getAccountNumber);
    }
    @PostMapping(value = "/transaction")
    @ResponseBody
    public ResponseData<List<Transaction>> getAccountTransaction(@Valid @RequestBody TransactionCritea critea) {
       return  accountService.getAccountTransactionWithPaging(critea);

    }



}
