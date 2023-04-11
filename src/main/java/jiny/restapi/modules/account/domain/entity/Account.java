package jiny.restapi.modules.account.domain.entity;

import jiny.restapi.modules.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String username;

    private String password;

    public static Account createAccount(String email,String nickname,String password){
        Account account = new Account();
        account.email = email;
        account.nickname = nickname;
        account.password = password;
        return account;
    }
}
