package com.example.infra.common.http;

/**
 * 请求错误类型
 */
public enum RetrofitErrorType {
    /**
     * token异常
     */
    TOKENERROR,
    /**
     * 网络异常
     */
    NETWORKERROR,
    /**
     * 服务器异常
     */
    SERVERERROR,
    /**
     * 未知异常
     */
    UNKNOW

}