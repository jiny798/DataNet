package jiny.restapi.modules.account.controller.dto;

import lombok.Data;

@Data
public class SignUpForm {
    private String nickname;
    private String email;
    private String password;
}
