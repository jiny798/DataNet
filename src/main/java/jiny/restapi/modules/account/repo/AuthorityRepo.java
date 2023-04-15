package jiny.restapi.modules.account.repo;


import jiny.restapi.modules.account.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepo extends JpaRepository<Authority,Long> {

    Authority findByAuthorityName(String name);
}
