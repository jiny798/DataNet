package jiny.restapi.modules.account.domain.entity;

import jiny.restapi.modules.common.entity.BaseTimeEntity;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
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

    @ManyToMany
    @JoinTable(
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "authorityName", referencedColumnName = "authorityName")})
    private Set<Authority> authorities = new HashSet<>();

    public void addAuthority(Authority authority){
        authorities.add(authority);
    }
    public List<SimpleGrantedAuthority> getAuthorityList(){
        List<SimpleGrantedAuthority> list = new ArrayList<>();

        Iterator<Authority> iter = authorities.iterator();
        while (iter.hasNext()){
            Authority authority = iter.next();
            list.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
        }
        return list;
    }

    public List<String> getAuthorityListToStr(){
        List<String> list = new ArrayList<>();

        Iterator<Authority> iter = authorities.iterator();
        while (iter.hasNext()){
            Authority authority = iter.next();
            list.add(authority.getAuthorityName());
        }
        return list;
    }

}
