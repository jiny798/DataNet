package jiny.restapi.modules.account.controller;

import jiny.restapi.modules.common.response.CommonResult;
import jiny.restapi.modules.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j @RestControllerAdvice @RequiredArgsConstructor
public class AccountExControllerAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(IllegalStateException.class)
    public CommonResult illegalStateExHandle(IllegalStateException ex){
        log.error("[throw exception] = ",ex);
        return responseService.getFailResult(409,ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult runtimeExHandle(RuntimeException ex){
        log.error("[throw exception] = ",ex);
        return responseService.getFailResult(500,"서버에 문제가 발생하였습니다.");
    }
}
