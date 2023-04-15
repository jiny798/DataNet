package jiny.restapi.modules.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @AllArgsConstructor @NoArgsConstructor
public class BoardResponseDto {
    @NotBlank
    private String boardName;
}
