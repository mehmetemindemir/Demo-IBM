package com.demo.ibm.dao;

import com.demo.ibm.model.ResponseData;
import com.demo.ibm.model.balance.Balance;
import com.demo.ibm.model.transaction.Transaction;
import com.demo.ibm.model.transaction.TransactionCritea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao implements IAccount {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResponseData<Balance> getAccountBalance(String accountNumber) {
        StringBuilder sql = null;
        ResponseData<Balance> res = new ResponseData<>();
        try {
            if (null != accountNumber) {
                sql = new StringBuilder();
                sql.append(" select c.* from schema_shopping.ibm_balance c ");
                sql.append(" where accountNumber=? ");
                Balance balance = jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Balance.class), new Object[]{accountNumber});
                res.setStatusCode(HttpStatus.NOT_FOUND.name());
                res.setData(null);
                if (null != balance) {
                    res.setStatusCode(HttpStatus.OK.name());
                    res.setData(balance);
                }
            } else {
                res.setStatusCode(HttpStatus.BAD_REQUEST.name());
                res.setData(null);
                res.setStatusMessage("Please check the account number");
            }


        } catch (Exception e) {
            res.setStatusCode(HttpStatus.EXPECTATION_FAILED.name());
            res.setData(null);
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ResponseData<List<Transaction>> getAccountTransaction(TransactionCritea critea) {
        ResponseData<List<Transaction>> res = new ResponseData<>();
        StringBuilder sql = null;
        List<Transaction> transactions = null;
        try {
            if (null != critea) {
                transactions = new ArrayList<>();
                Object[] param = new Object[]{critea.getAccountNumber(), critea.getStartTransactionTs(), critea.getEndTransactionTs()};
                sql = new StringBuilder();
                sql.append(" select c.accountNumber,c.transactionTs,c.type,c.amount,c.num from schema_shopping.ibm_transaction c ");
                if (null != critea.getType() && critea.getType().length() > 0) {
                    sql.append(" where accountNumber=? and type=? and transactionTs between ? and ?  order by num  ");
                    param = new Object[]{critea.getAccountNumber(), critea.getType(), critea.getStartTransactionTs(), critea.getEndTransactionTs()};
                } else {
                    sql.append(" where accountNumber=? and transactionTs between ? and ? order by num ");
                }
                transactions = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(Transaction.class), param);
                if (transactions.size() > 0) {
                    res.setStatusCode(HttpStatus.OK.name());
                    res.setData(transactions);
                } else {
                    res.setStatusCode(HttpStatus.NOT_FOUND.name());
                    res.setData(null);

                }
            } else {
                res.setStatusCode(HttpStatus.BAD_REQUEST.name());
                res.setData(null);
                res.setStatusMessage("Please check the criteria");
            }

        } catch (Exception e) {
            res.setStatusCode(HttpStatus.EXPECTATION_FAILED.name());
            res.setData(null);
            res.setStatusMessage("the system failure");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ResponseData<List<Transaction>> getAccountTransactionWithPaging(TransactionCritea critea) {
        ResponseData<List<Transaction>> res = new ResponseData<>();
        StringBuilder sql = null;
        List<Transaction> transactions = null;
        int offset = 0;
        try {
            int totalTransaction = totalTransaction(critea);
            int totalPage = (totalTransaction / 10);
            if (totalTransaction % 10 != 0) {
                totalPage++;
            }
            res.setTotalPage(totalPage);
            res.setCurrentPage(critea.getPage());
            offset = (critea.getPage() - 1) * 10;
            if (offset < 0) {
                offset = 0;
            }

            transactions = new ArrayList<>();
            Object[] param = new Object[]{critea.getAccountNumber(), critea.getStartTransactionTs(), critea.getEndTransactionTs(), offset};
            sql = new StringBuilder();
            sql.append(" select c.accountNumber,c.transactionTs,c.type,c.amount,c.num from schema_shopping.ibm_transaction c ");
            if (null != critea.getType() && critea.getType().length() > 0) {
                sql.append(" where accountNumber=? and type=? and transactionTs between ? and ?  order by num limit 10 offset ? ");
                param = new Object[]{critea.getAccountNumber(), critea.getType(), critea.getStartTransactionTs(), critea.getEndTransactionTs(), offset};
            } else {
                sql.append(" where accountNumber=? and transactionTs between ? and ? order by num limit 10 offset ?");
            }

            transactions = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(Transaction.class), param);
            if (transactions.size() > 0) {
                res.setStatusCode(HttpStatus.OK.name());
                res.setData(transactions);
            } else {
                res.setStatusCode(HttpStatus.NOT_FOUND.name());
                res.setData(null);

            }

        } catch (Exception e) {
            res.setStatusCode(HttpStatus.EXPECTATION_FAILED.name());
            res.setData(null);
            e.printStackTrace();
        }

        return res;
    }

    private int totalTransaction(TransactionCritea critea) {
        int totalPage = 0;
        StringBuilder sql = null;
        try {
            Object[] param = new Object[]{critea.getAccountNumber(), critea.getStartTransactionTs(), critea.getEndTransactionTs()};
            sql = new StringBuilder();
            sql.append(" select count(*) from schema_shopping.ibm_transaction c ");
            if (null != critea.getType() && critea.getType().length() > 0) {
                sql.append(" where accountNumber=? and type=? and transactionTs between ? and ?  ");
                param = new Object[]{critea.getAccountNumber(), critea.getType(), critea.getStartTransactionTs(), critea.getEndTransactionTs()};
            } else {
                sql.append(" where accountNumber=? and transactionTs between ? and ?");
            }
            totalPage = jdbcTemplate.queryForObject(sql.toString(), Integer.class, param);
        } catch (Exception e) {
            throw e;
        }
        return totalPage;
    }
}
