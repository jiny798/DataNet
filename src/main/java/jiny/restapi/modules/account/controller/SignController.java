package jiny.restapi.modules.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jiny.restapi.config.security.JwtTokenProvider;
import jiny.restapi.modules.account.controller.dto.SignInForm;
import jiny.restapi.modules.account.controller.dto.SignInResponseDto;
import jiny.restapi.modules.account.controller.dto.SignUpForm;
import jiny.restapi.modules.account.controller.dto.SignUpResponseDto;
import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.account.repo.AccountRepo;
import jiny.restapi.modules.account.service.AccountService;
import jiny.restapi.modules.common.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor @Slf4j
@Tag(name = "회원", description = "회원 관련 API")
public class SignController {

    private final AccountService accountService;
    private final ResponseService responseService;
    private final AccountRepo accountRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signUp")
    @Operation(summary = "회원 가입 요청", description = "회원 가입을 요청합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "가입이 정상적으로 처리되었습니다.",
                    content = @Content(schema = @Schema(implementation = SingleResult.class))),
            @ApiResponse(responseCode = "400", description = "유효성 검증에 실패하였습니다.",
                    content = @Content(examples = @ExampleObject(value = "{\n"+"  \"success\" : false,\n" +"  \"code\" : -1,\n"+"  \"msg\" : \"유효성 검증에 실패하였습니다.\"\n" + "}" ))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 {이메일|닉네임}입니다",
                    content = @Content(examples = @ExampleObject(value = "{\n"+"  \"success\" : false,\n" +"  \"code\" : -1,\n"+"  \"msg\" : \"이미 존재하는 이메일입니다.\"\n" + "}" ))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(examples = @ExampleObject(value = "{\n"+"  \"success\" : false,\n" +"  \"code\" : -1,\n"+"  \"msg\" : \"서버에 문제가 발생하였습니다.\"\n" + "}" ))),
    })
    public CommonResult signUp(@RequestBody @Validated SignUpForm signUpForm,
                               BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            log.info("SignUp Error - {}",bindingResult);
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        Account account = accountService.signUp(signUpForm);
        log.info(signUpForm.getEmail()+" "+signUpForm.getNickname()+" "+signUpForm.getPassword());
        SignUpResponseDto responseDto = new SignUpResponseDto(account.getNickname(),account.getEmail());

        return responseService.getSingleResult(responseDto);
    }

    @PostMapping(value = "/signIn")
    public SingleResult<SignInResponseDto> signIn(@Parameter(description = "폼") @RequestBody SignInForm signInForm){
        Account account = accountRepo.findByNickname(signInForm.getNickname());
        if(account==null){
            throw new IllegalArgumentException("아이디 및 비밀번호가 일치하지 않습니다.");
        }
        if(!passwordEncoder.matches(signInForm.getPassword(),account.getPassword())){
            throw new IllegalArgumentException("아이디 및 비밀번호가 일치하지 않습니다.");
        }
        String token = jwtTokenProvider.createToken(String.valueOf(account.getNickname()), account.getAuthorityListToStr());
        String username = account.getNickname();
        SignInResponseDto responseDto = new SignInResponseDto(username,token);

        return responseService.getSingleResult(responseDto);
    }
}
