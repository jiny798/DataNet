package jiny.restapi.modules.board.service;

import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.account.repo.AccountRepo;
import jiny.restapi.modules.board.controller.dto.BoardRequestDto;
import jiny.restapi.modules.board.controller.dto.BoardResponseDto;
import jiny.restapi.modules.board.controller.dto.PostRequestDto;
import jiny.restapi.modules.board.controller.dto.PostResponseDto;
import jiny.restapi.modules.board.domain.Board;
import jiny.restapi.modules.board.domain.Post;
import jiny.restapi.modules.board.repo.BoardJpaRepo;
import jiny.restapi.modules.board.repo.PostJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepo boardRepo;
    private final PostJpaRepo postRepo;
    private final AccountRepo accountRepo;

    public Long createBoard(String boardName){
        Board board = Board.createBoard(boardName);
        Board saveBoard = boardRepo.save(board);
        return saveBoard.getId();
    }

    public Board findBoard(String boardName){
        return Optional.ofNullable(boardRepo.findByName(boardName)).orElseThrow();
    }

    public List<BoardResponseDto> getBoards(){
        List<Board> board_list = boardRepo.findAll();
        List<BoardResponseDto> dto_list = board_list.stream()
                .map(o->new BoardResponseDto(o.getName()))
                .collect(Collectors.toList());
        return dto_list;
    }

    public List<PostResponseDto> findPosts(String boardName) {
        //PostResponseDto 로 변환
        List<Post> post_list = postRepo.findByBoard(findBoard(boardName));
        List<PostResponseDto> dto_list = post_list.stream()
                .map(o-> new PostResponseDto(o.getId(),o.getAccount().getUsername(),o.getTitle(),o.getContent()))
                .collect(Collectors.toList());

        return dto_list;
    }

    public Post getPost(long postId) {
        return postRepo.findById(postId).orElseThrow();
    }
    public Post writePost(Long accountId, String boardName, PostRequestDto requestDto) {
        Board board = findBoard(boardName);
        Account account = accountRepo.findById(accountId).orElseThrow();

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(),board,account);
        return postRepo.save(post);
    }


    public Post updatePost(Long postId, Long accountId, PostRequestDto requestDto) {
        Post post = getPost(postId);
        Account account = post.getAccount();
        if (!accountId.equals(account.getId())) {
            //해당 리소스 오너가 아님
        }
        Board board = boardRepo.findByName(requestDto.getBoardName());
        post.updatePost(board,requestDto.getTitle(), requestDto.getContent());
        return post;
    }

    // 게시물을 삭제합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deletePost(Long postId, Long accountId) {
        Post post = getPost(postId);
        Account account = post.getAccount();
        if (!accountId.equals(account.getId())){
            //유저를 찾을 수 없음
        }

        postRepo.delete(post);
        return true;
    }

}
