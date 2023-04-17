package jiny.restapi.modules.board.service;

import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.account.repo.AccountRepo;
import jiny.restapi.modules.board.controller.dto.*;
import jiny.restapi.modules.board.domain.Board;
import jiny.restapi.modules.board.domain.Post;
import jiny.restapi.modules.board.repo.BoardJpaRepo;
import jiny.restapi.modules.board.repo.PostJpaRepo;
import jiny.restapi.modules.common.exception.board.DeniedAccessEx;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    //게시글 상세 조회
    public PostResponseDto getPost(long postId) {
        Post findPost = postRepo.findById(postId).orElseThrow(()->new RuntimeException());
        PostResponseDto responseDto = new PostResponseDto(postId,
                                        findPost.getAccount().getUsername(),
                                        findPost.getTitle(),
                                        findPost.getContent());

        return responseDto;
    }

    //게시글 작성
    public Long writePost( PostRequestDto requestDto) {
        Board board = findBoard(requestDto.getBoardName());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;

        String username = userDetails.getUsername();
        Account account = accountRepo.findByNickname(username);
        if(account == null){
            throw new RuntimeException();
        }

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(),board,account);

        return postRepo.save(post).getId();
    }

    //게시물 수정
    public Long updatePost(UpdateRequestDto updateRequestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;

        String username = userDetails.getUsername();
        Account SecurityHolder_account = accountRepo.findByNickname(username);
        if(SecurityHolder_account == null){
            throw new DeniedAccessEx("자신의 게시물만 수정할 수 있습니다.");
        }
        Long requestPostId = updateRequestDto.getPostId();

        Post post = postRepo.findById(requestPostId).orElseThrow(()-> new RuntimeException("처리중 오류가 발생하였습니다."));
        Long requestAccountIdOfPost= post.getAccount().getId();

        if (!requestAccountIdOfPost.equals(SecurityHolder_account.getId())) { //요청온 post의 accountId와 현재 시큐리티홀더의 accountId 비교
            //해당 리소스 오너가 아님
            throw new DeniedAccessEx("자신의 게시물만 수정할 수 있습니다.");
        }
        Board board = boardRepo.findByName(updateRequestDto.getBoardName());
        post.updatePost(board,updateRequestDto.getTitle(), updateRequestDto.getContent());
        return post.getId();
    }

    // 게시물을 삭제합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
    public boolean deletePost(Long requestPostId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;

        String username = userDetails.getUsername();
        Account SecurityHolder_account = accountRepo.findByNickname(username);
        if(SecurityHolder_account == null){
            throw new DeniedAccessEx("자신의 게시물만 제거할 수 있습니다.");
        }

        Post findPost = postRepo.findById(requestPostId).orElseThrow(()-> new RuntimeException("처리중 오류가 발생하였습니다."));
        Account account = findPost.getAccount();

        if (!SecurityHolder_account.getId().equals(account.getId())){
            //유저를 찾을 수 없음
            throw new DeniedAccessEx("자신의 게시물만 제거할 수 있습니다.");
        }

        postRepo.delete(findPost);
        return true;
    }

}
