package com.gec.system.exception;

import com.gec.system.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalException {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("全局执行");
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        System.out.println("特定异常执行");
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    //自定义异常
    @ExceptionHandler(MyCustomerException.class)
    @ResponseBody
    public Result error(MyCustomerException e){
        System.out.println("进入自定义异常处理");
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMessage());
    }
}