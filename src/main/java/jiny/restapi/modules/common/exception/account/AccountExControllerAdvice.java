package jiny.restapi.modules.common.exception.account;

import jiny.restapi.modules.common.response.CommonResult;
import jiny.restapi.modules.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j @RestControllerAdvice @RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountExControllerAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(DuplicatedAccountEx.class)
    public ResponseEntity<CommonResult> illegalStateExHandle(DuplicatedAccountEx ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(409,ex.getMessage()), HttpStatus.CONFLICT);
    }

}
