package com.demo.ibm.model.transaction;

import java.util.Date;

public class TransactionCritea  {
    private String startTransactionTs;
    private String endTransactionTs;
    private String accountNumber;
    private String type;
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTransactionTs() {
        return startTransactionTs;
    }

    public void setStartTransactionTs(String startTransactionTs) {
        this.startTransactionTs = startTransactionTs;
    }

    public String getEndTransactionTs() {
        return endTransactionTs;
    }

    public void setEndTransactionTs(String endTransactionTs) {
        this.endTransactionTs = endTransactionTs;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

}
