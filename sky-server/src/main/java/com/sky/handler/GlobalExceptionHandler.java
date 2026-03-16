package com.sky.handler;

import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //Get all exception
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("Exception Log:"+ex.getMessage());
        return Result.error(ex.getMessage());
    }

}
