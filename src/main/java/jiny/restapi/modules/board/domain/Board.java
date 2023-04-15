package jiny.restapi.modules.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    public static Board createBoard(String name){
        Board board =new Board();
        board.setName(name);
        return board;
    }

    protected void setName(String name){
        this.name = name;
    }
}
