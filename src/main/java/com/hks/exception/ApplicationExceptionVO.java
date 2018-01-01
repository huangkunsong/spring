package com.hks.exception;

import org.springframework.util.ObjectUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ApplicationExceptionVO {
    private String status;
    private String code;
    private String message;
    private String detailMessage;
    private String moreInfo;

    public ApplicationExceptionVO(){}

    public ApplicationExceptionVO(Throwable exception){
        this.status = "500";
        this.code = "500";
        this.message = getMessage(exception);
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw, true));
        this.detailMessage = sw.toString();
    }

    private String getMessage(Throwable exception){
        String message = exception.getMessage();
        if (ObjectUtils.isEmpty(message) &&
            !ObjectUtils.isEmpty(exception.getCause())) {
            message = exception.getCause().getMessage();
        }
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
