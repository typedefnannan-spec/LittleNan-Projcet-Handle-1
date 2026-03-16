package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //解决所有异常
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常日志（所有异常类）："+ex.getMessage());
        return Result.error(ex.getMessage());
    }

    //解决数据库异常
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String exInfo=ex.getMessage();
        //异常信息如果包括该字段（重复字段）
        if(exInfo.contains("Duplicate entry")){
            String split[]=exInfo.split(" ");
            String returnMsg=split[2]+MessageConstant.ALREADY_EXISTS;
            log.error("异常日志（数据库异常类）："+returnMsg);
            return Result.error(returnMsg);
        } else{
            //未知错误
            log.error(MessageConstant.UNKNOWN_ERROR);
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }


}
