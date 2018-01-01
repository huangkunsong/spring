package com.hks.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Huangkunsong
 */
@ControllerAdvice
public class MvcControllerException{
    private final static Logger logger = LoggerFactory.getLogger(MvcControllerException.class);

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApplicationExceptionVO resolveException(Throwable ex) {
        logger.error(ex.getMessage(),ex);
        return new ApplicationExceptionVO(ex);
    }
}
