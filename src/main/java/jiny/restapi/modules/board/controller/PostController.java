package jiny.restapi.modules.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jiny.restapi.modules.board.controller.dto.PostRequestDto;
import jiny.restapi.modules.board.controller.dto.PostResponseDto;
import jiny.restapi.modules.board.controller.dto.UpdateRequestDto;
import jiny.restapi.modules.board.service.BoardService;
import jiny.restapi.modules.common.response.CommonResult;
import jiny.restapi.modules.common.response.ListResult;
import jiny.restapi.modules.common.response.ResponseService;
import jiny.restapi.modules.common.response.SingleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board") @Slf4j
@Tag(name = "게시물", description = "게시물 관련 API")
public class PostController {
    private final BoardService boardService;
    private final ResponseService responseService;

    //게시물 리스트 조회
    @GetMapping(value = "/{boardName}/post")
    @Operation(summary = "게시물 리스트 조회", description = "게시판 종류에 따라 게시물 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 리스트를 조회하였습니다.",
                    content = @Content(schema = @Schema(implementation = ListResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    public ListResult<PostResponseDto> getPosts(@PathVariable String bordName){
        List<PostResponseDto> list = boardService.findPosts(bordName);
        return responseService.getListResult(list);
    }

    //게시물 생성
    @PostMapping(value = "/post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 생성을 완료하고 식별자를 반환합니다",
                    content = @Content(schema = @Schema(implementation = SingleResult.class))),
            @ApiResponse(responseCode = "400", description = "제목 및 내용을 입력해주세요 (유효성 검증 실패).",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    public SingleResult<Long> createPost(@RequestBody @Validated PostRequestDto postRequestDto, BindingResult result){
        if(result.hasErrors()){
            log.info("Post Error - {}",result);
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }

        Long postId = boardService.writePost(postRequestDto);

        return responseService.getSingleResult(postId);
    }

    //게시물 상세 조회
    @GetMapping(value = "/post/{postId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 등록을 완료하였습니다.",
                    content = @Content(schema = @Schema(implementation = SingleResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    public SingleResult<PostResponseDto> getPostDetails(@PathVariable Long postId){
        return responseService.getSingleResult(boardService.getPost(postId));
    }

    //게시물 수정
    @PutMapping(value = "/post/{postId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 수정을 완료하고 식별자를 반환합니다",
                    content = @Content(schema = @Schema(implementation = SingleResult.class))),
            @ApiResponse(responseCode = "400", description = "제목 및 내용을 입력해주세요 (유효성 검증 실패).",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "자신의 게시물만 수정 가능합니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    public SingleResult<Long> updatePost(@PathVariable Long postId ,@RequestBody @Validated UpdateRequestDto updateRequestDto, BindingResult result){
        if(result.hasErrors()){
            log.info("Post Error - {}",result);
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }
        boardService.updatePost(updateRequestDto);
        return responseService.getSingleResult(postId);
    }


    //게시물 제거
    @DeleteMapping(value = "/post/{postId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 제거를 완료하고 제거 완료시 true를 반환합니다",
                    content = @Content(schema = @Schema(implementation = SingleResult.class))),
            @ApiResponse(responseCode = "403", description = "자신의 게시물만 제거 가능합니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    public SingleResult<Boolean> deletePost(@PathVariable Long postId){
        boolean resultTag = boardService.deletePost(postId);
        return responseService.getSingleResult(resultTag);
    }
}
