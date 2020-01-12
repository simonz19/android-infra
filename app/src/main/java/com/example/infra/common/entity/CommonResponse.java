package com.example.infra.common.entity;


public class CommonResponse<T> {

    private String resultMessage;
    private int resultCode;
    private T data;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    /**
     * is response ok or not
     *
     * @return
     */
    public boolean isSuccess() {
        return resultCode == 0;
    }

}
