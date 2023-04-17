package jiny.restapi.modules.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
}
