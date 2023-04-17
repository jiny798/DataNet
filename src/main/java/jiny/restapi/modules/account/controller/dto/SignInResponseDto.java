package jiny.restapi.modules.account.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SignInResponseDto {
    public String nickname;
    public String token;
}
