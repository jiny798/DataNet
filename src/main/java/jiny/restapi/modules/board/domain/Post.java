package jiny.restapi.modules.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jiny.restapi.modules.account.domain.entity.Account;
import jiny.restapi.modules.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") @JsonIgnore
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id") @JsonIgnore
    private Account account;

    public Post updatePost(Board board,String title,String content){
        this.title = title;
        this.content = content;
        this.board = board;
        return this;
    }

    public Post(String title,String content,Board board,Account account){
        this.title=title;
        this.content=content;
        this.board=board;
        this.account=account;
    }
}
