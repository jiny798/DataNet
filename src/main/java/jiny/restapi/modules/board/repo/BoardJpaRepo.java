package jiny.restapi.modules.board.repo;

import jiny.restapi.modules.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepo extends JpaRepository<Board,Long> {
    Board findByName(String name);
}
