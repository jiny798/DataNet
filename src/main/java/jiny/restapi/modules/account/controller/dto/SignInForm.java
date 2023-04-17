package jiny.restapi.modules.account.controller.dto;

import lombok.Data;

@Data
public class SignInForm {
    public String nickname;
    public String password;
}
