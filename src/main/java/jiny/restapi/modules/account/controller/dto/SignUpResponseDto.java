package jiny.restapi.modules.account.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class SignUpResponseDto {
    public String nickname;
    public String email;
}
