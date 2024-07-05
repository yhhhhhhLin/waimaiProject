package xyz.linyh.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.linyh.Exception.MyException;
import xyz.linyh.Result.R;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlingController {


    @ExceptionHandler(MyException.class)
    public R myException(Exception e){
        log.info("处理了一个自定义异常:{}",e.getMessage());
        System.out.println(R.error(e.getMessage()));
        return R.error(e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R SQLIntegrityConstraintViolationException(Exception e){
        log.info("处理了一个自定义异常:{}",e.getMessage());
        return R.error("已经存在");
    }
}
