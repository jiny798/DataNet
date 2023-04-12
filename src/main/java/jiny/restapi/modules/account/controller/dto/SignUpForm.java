package jiny.restapi.modules.account.controller.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpForm {

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")  @Schema(description = "닉네임 3-20자", example = "abd223")
    private String nickname;

    @Email
    @NotBlank @Schema(description = "이메일", example = "abc@jiniworld.me")
    private String email;

    @NotBlank
    @Length(min = 8, max = 50) @Schema(description = "패스워드 입력 8-50자")
    private String password;
}
