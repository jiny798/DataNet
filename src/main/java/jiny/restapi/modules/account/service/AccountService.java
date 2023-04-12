package jiny.restapi.modules.account.service;

import jiny.restapi.modules.account.controller.dto.SignUpForm;
import jiny.restapi.modules.account.domain.UserAccount;
import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.account.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional @Slf4j
public class AccountService implements UserDetailsService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Account account = Optional.ofNullable(accountRepo.findByNickname(username)).get();
        if (account == null) {
            throw new RuntimeException();
        }
        return new UserAccount(account);
    }

    public Account signUp(SignUpForm signUpForm) {
        Account newAccount = saveNewAccount(signUpForm);
        return newAccount;
    }

    private Account saveNewAccount(SignUpForm signUpForm) {
        Account account = Account.createAccount(signUpForm.getEmail(), signUpForm.getNickname(), passwordEncoder.encode(signUpForm.getPassword()));
        validateDuplicatedEmail(account);
        validateDuplicatedNickname(account);
        return accountRepo.save(account);
    }

    private void validateDuplicatedEmail(Account account){
        Account findAccount = accountRepo.findByEmail(account.getEmail());
        if (findAccount != null) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    private void validateDuplicatedNickname(Account account){
        Account findAccount = accountRepo.findByNickname(account.getNickname());
        if (findAccount != null) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }
}
