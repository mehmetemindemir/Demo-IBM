package com.demo.ibm.services;

import com.demo.ibm.dao.IAccount;
import com.demo.ibm.model.ResponseData;
import com.demo.ibm.model.balance.Balance;
import com.demo.ibm.model.transaction.Transaction;
import com.demo.ibm.model.transaction.TransactionCritea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final IAccount account;

    @Autowired
    public AccountService(IAccount account){
        this.account=account;
    }

    public ResponseData<Balance> getAccountBalance(String accountNumber){
        return account.getAccountBalance(accountNumber);
    }
    public ResponseData<List<Transaction>> getAccountTransaction(TransactionCritea critea){
        return account.getAccountTransaction(critea);
    }
    public ResponseData<List<Transaction>> getAccountTransactionWithPaging(TransactionCritea critea){
        return account.getAccountTransactionWithPaging(critea);
    }
}
