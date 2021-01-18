package com.demo.ibm.model;

import java.io.Serializable;
import java.util.List;

public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = 8805336031799121824L;
    private String statusCode;
    private String statusMessage;
    private T data;
    private int totalPage;
    private int currentPage;

    private int dataCount = -1;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage > 0) {
            this.currentPage = currentPage;
        } else {
            this.currentPage = 1;
        }

    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getDataCount() {
        if (dataCount == -1 && getData() != null) {
            if (getData() instanceof List) {
                dataCount = ((List) getData()).size();
            } else {
                dataCount = 1;
            }
        }

        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

}
