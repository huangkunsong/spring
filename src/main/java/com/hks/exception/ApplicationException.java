package com.hks.exception;

public class ApplicationException extends Throwable {
    //异常信息
    private String message;

    public ApplicationException(String message){
        super(message);
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
