package jiny.restapi.modules.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jiny.restapi.modules.board.controller.dto.BoardRequestDto;
import jiny.restapi.modules.board.controller.dto.BoardResponseDto;
import jiny.restapi.modules.board.controller.dto.PostResponseDto;
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
@RequiredArgsConstructor @Slf4j
@Tag(name = "게시판", description = "게시판 관련 API")
public class BoardController {

    private final BoardService boardService;
    private final ResponseService responseService;

    @PostMapping(value = "/board")
    @Operation(summary = "게시판 생성", description = "게시판을 생성합니다. 빈값을 허용하지 않습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "가입이 정상적으로 처리되었습니다.",
                    content = @Content(schema = @Schema(implementation = SingleResult.class))),
            @ApiResponse(responseCode = "400", description = "유효성 검증에 실패하였습니다.",
                    content = @Content(examples = @ExampleObject(value = "{\n"+"  \"success\" : false,\n" +"  \"code\" : -1,\n"+"  \"msg\" : \"유효성 검증에 실패하였습니다.\"\n" + "}" ))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(examples = @ExampleObject(value = "{\n"+"  \"success\" : false,\n" +"  \"code\" : -1,\n"+"  \"msg\" : \"서버에 문제가 발생하였습니다.\"\n" + "}" ))),
    })
    public CommonResult setBoard(@Validated @RequestBody BoardRequestDto requestDto, BindingResult result){
        if(result.hasErrors()){
            log.info("Board Error - {}",result);
            throw new IllegalArgumentException("유효하지 않은 요청입니다.");
        }
        boardService.createBoard(requestDto.getBoardName());
        return responseService.getSuccessResult(200,"게시판 생성을 완료하였습니다.");
    }

    @GetMapping(value = "/board")
    @Operation(summary = "게시판 목록 조회", description = "게시판 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "가입이 정상적으로 처리되었습니다.",
                    content = @Content(schema = @Schema(implementation = ListResult.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부에 문제가 발생하였습니다.",
                    content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    public ListResult getBoards(){
        List<BoardResponseDto> list = boardService.getBoards();
        return responseService.getListResult(list);
    }


}
