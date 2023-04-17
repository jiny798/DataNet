package jiny.restapi.modules.board.repo;

import jiny.restapi.modules.board.domain.Board;
import jiny.restapi.modules.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJpaRepo extends JpaRepository<Post,Long> {
    List<Post> findByBoard(Board board);
}
