package jiny.restapi.modules.account.controller;

import jiny.restapi.modules.account.controller.exception.DuplicatedAccountEx;
import jiny.restapi.modules.common.response.CommonResult;
import jiny.restapi.modules.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j @RestControllerAdvice @RequiredArgsConstructor
public class AccountExControllerAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(DuplicatedAccountEx.class)
    public ResponseEntity<CommonResult> illegalStateExHandle(DuplicatedAccountEx ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(-1,ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResult> illegalArgumentExHandle(IllegalArgumentException ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(-1,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CommonResult> runtimeExHandle(IllegalStateException ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(-1 , ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
