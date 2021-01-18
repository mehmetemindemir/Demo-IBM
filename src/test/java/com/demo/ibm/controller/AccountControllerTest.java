package com.demo.ibm.controller;

import com.demo.ibm.model.transaction.TransactionCritea;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mvc;
    Gson gson = new Gson();
    TransactionCritea transactionCritea;
    @Test
    void getAccountTransaction() throws Exception {
        transactionCritea=new TransactionCritea();
        transactionCritea.setAccountNumber("test");
        transactionCritea.setStartTransactionTs("2021-01-01 01:55:51");
        transactionCritea.setPage(0);
        transactionCritea.setEndTransactionTs("2021-01-15 11:55:51");
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/account/transaction")
                .contentType("application/json")
                .content(gson.toJson(transactionCritea));

        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("OK")));

    }
    @Test
    void getAccountTransactionWrongAccountNumber() throws Exception {
        transactionCritea=new TransactionCritea();
        transactionCritea.setAccountNumber("xxx");
        transactionCritea.setStartTransactionTs("2021-01-01 01:55:51");
        transactionCritea.setPage(0);
        transactionCritea.setEndTransactionTs("2021-01-15 11:55:51");
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/account/transaction")
                .contentType("application/json")
                .content(gson.toJson(transactionCritea));

        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("NOT_FOUND")));

    }
    @Test
    void getAccountTransactionWrongStartDate() throws Exception {
        transactionCritea=new TransactionCritea();
        transactionCritea.setAccountNumber("xxx");
        transactionCritea.setStartTransactionTs(null);
        transactionCritea.setPage(0);
        transactionCritea.setEndTransactionTs("2021-01-15 11:55:51");
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/account/transaction")
                .contentType("application/json")
                .content(gson.toJson(transactionCritea));

        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("NOT_FOUND")));

    }
    @Test
    void getAccountTransactionWithType() throws Exception {
        transactionCritea=new TransactionCritea();
        transactionCritea.setAccountNumber("test");
        transactionCritea.setType("WITHDRAW");
        transactionCritea.setStartTransactionTs("2021-01-01 01:55:51");
        transactionCritea.setPage(0);
        transactionCritea.setEndTransactionTs("2021-01-15 11:55:51");
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/account/transaction")
                .contentType("application/json")
                .content(gson.toJson(transactionCritea));

        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("OK")));

    }
    @Test
    void getAccountTransactionPaging() throws Exception {
        transactionCritea=new TransactionCritea();
        transactionCritea.setAccountNumber("test");
        transactionCritea.setType("WITHDRAW");
        transactionCritea.setStartTransactionTs("2021-01-01 01:55:51");
        transactionCritea.setPage(1);
        transactionCritea.setEndTransactionTs("2021-01-15 11:55:51");
        RequestBuilder requestBuilder= MockMvcRequestBuilders
                .post("/account/transaction")
                .contentType("application/json")
                .content(gson.toJson(transactionCritea));

        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("OK")));

    }
    @Test
    void getAccountBalance() throws Exception {
        RequestBuilder requestBuilder= MockMvcRequestBuilders.get("/account/balance/test");
        mvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is("OK")));
    }
}