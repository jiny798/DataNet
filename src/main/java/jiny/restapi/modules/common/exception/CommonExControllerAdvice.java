package jiny.restapi.modules.common.exception;

import jiny.restapi.modules.common.response.CommonResult;
import jiny.restapi.modules.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CommonExControllerAdvice {
    private final ResponseService responseService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResult> illegalArgumentExHandle(IllegalArgumentException ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(400,ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CommonResult> runtimeExHandle(IllegalStateException ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(500 , ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonResult> runtimeExHandle(RuntimeException ex){
        log.error("[throw exception] = ",ex);
        return new ResponseEntity<>(responseService.getFailResult(400 , ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
