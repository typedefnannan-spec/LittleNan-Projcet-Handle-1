package com.WM.aspect;

import com.WM.annotation.AutoFill;
import com.WM.constant.AutoFillConstant;
import com.WM.enumeration.OperationType;
import com.WM.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    //定义切入点（定义包和注解共同使用）
    @Pointcut("execution(* com.WM.dao.*.*(..)) && @annotation(com.WM.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("自动填充");
        //获得注解对象
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();
        AutoFill autoFill=signature.getMethod().getAnnotation(AutoFill.class);
        Object[] args=joinPoint.getArgs();
        //假设对象在第0个位置
        Object obj=args[0];
        if(autoFill.value().equals(OperationType.INSERT)){
            //获得对应方法对象，通过反射进行赋值
            try {
                Method setCreateTime = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                setCreateTime.invoke(obj, LocalDateTime.now());
            }   catch (Exception e) {
                log.info("出现异常：{}",e.getMessage());
            }
            try {
                Method setCreateUser=obj.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                setCreateUser.invoke(obj, ThreadLocalUtil.getCurrentId());
            } catch (Exception e) {
                log.info("出现异常：{}",e.getMessage());
            }
        }
        //获得对应方法对象，通过反射进行赋值
        try {
            Method setUpdateTime = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            setUpdateTime.invoke(obj, LocalDateTime.now());
        } catch (Exception e){
            log.info("出现异常：{}",e.getMessage());
        }
        try{
            Method setUpdateUser = obj.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            setUpdateUser.invoke(obj, ThreadLocalUtil.getCurrentId());
        } catch (Exception e){
            log.info("出现异常：{}",e.getMessage());
        }
    }

}
