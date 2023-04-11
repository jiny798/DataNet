package jiny.restapi.modules.account.controller;

import jiny.restapi.modules.account.controller.dto.SignUpForm;
import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.account.service.AccountService;
import jiny.restapi.modules.common.response.CommonResponse;
import jiny.restapi.modules.common.response.CommonResult;
import jiny.restapi.modules.common.response.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor @Slf4j
public class SignController {

    private final AccountService accountService;
    private final ResponseService responseService;

    @PostMapping(value = "/signUp")
    public CommonResult signUp(@RequestBody SignUpForm signUpForm) {

        Account account = accountService.signUp(signUpForm);
        log.info(signUpForm.getEmail()+" "+signUpForm.getNickname()+" "+signUpForm.getPassword());
        return responseService.getSuccessResult(200,"가입이 정상적으로 완료되었습니다.");
    }
}
