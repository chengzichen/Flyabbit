package com.dhc.library.data.net;

/**
 * @creator:denghc(desoce)
 * @updateTime:2018/7/30 13:50
 * @description:  network anomaly
 */
public class NetError extends Exception {
    private Throwable exception;
    private String type = NoConnectError;

    public static final String ParseError = "0";   //数据解析异常
    public static final String NoConnectError = "-1";   //无连接异常
    public static final String AuthError = "-2";   //用户验证异常
    public static final String NoDataError = "-3";   //无数据返回异常
    public static final String BusinessError = "-4";   //业务异常
    public static final String SocketError = "-6";   //连接超时异常
    public static final String OtherError = "-5";   //其他异常
    public static final String NetError = "-7";   //请求错误

    public NetError(Throwable exception, String type) {
        this.exception = exception;
        this.type = type;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public NetError(String detailMessage, String type) {
        super(detailMessage);
        this.type = type;
    }

    @Override
    public String getMessage() {
        if (exception != null) {
            if (exception.getMessage() == null) {
                return exception.toString();
            } else {
                return exception.getMessage();
            }
        }
        if (super.getMessage() != null) {
            return super.getMessage();
        } else {
            return "未知错误";
        }
    }

    public String getType() {
        return type;
    }
}
