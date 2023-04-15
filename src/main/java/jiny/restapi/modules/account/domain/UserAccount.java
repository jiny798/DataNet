package jiny.restapi.modules.account.domain;

import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.account.domain.entity.Authority;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class UserAccount extends User {
    @Getter
    private final Account account;

    public UserAccount(Account account) {
        super(account.getNickname(), account.getPassword(), account.getAuthorityList());
//        super(account.getNickname(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

        this.account = account;
    }

}
