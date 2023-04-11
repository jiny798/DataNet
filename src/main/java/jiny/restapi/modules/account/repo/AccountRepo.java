package jiny.restapi.modules.account.repo;

import jiny.restapi.modules.account.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account,Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Account findByEmail(String email);
    Account findByNickname(String nickname);
}
