package com.demo.ibm.dao;


import com.demo.ibm.model.ResponseData;
import com.demo.ibm.model.balance.Balance;
import com.demo.ibm.model.transaction.Transaction;
import com.demo.ibm.model.transaction.TransactionCritea;

import java.util.List;

public interface IAccount {
    ResponseData<Balance> getAccountBalance(String accountNumber);
    ResponseData<List<Transaction>> getAccountTransaction(TransactionCritea critea);
    ResponseData<List<Transaction>> getAccountTransactionWithPaging(TransactionCritea critea);
}
