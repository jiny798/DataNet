package jiny.restapi.modules.board.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data @NoArgsConstructor
public class PostRequestDto {

    @NotEmpty @Schema(description = "카테고리 메뉴", example = "게시판")
    private String boardName;
    @Length(min = 2, max = 20) @Schema(description = "제목 2자 이상", example = "제목")
    private String title;
    @Length(min = 2, max = 20) @Schema(description = "게시물 내용", example = "안녕하세요")
    private String content;
}
