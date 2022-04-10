package com.bushy.fnbgame.domain;

import com.bushy.fnbgame.domain.constants.PlayerTurn;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "boards")
public class Board {

    @Id
    private String id;

    private Line playerOne;
    private Line playerTwo;

    private PlayerTurn next;
    private boolean finished;

    public Board() {
        this.playerOne = new Line();
        this.playerTwo = new Line();
        this.next = PlayerTurn.ONE;
    }

    public boolean hasCleanLine() {
        return playerOne.hasCleanLine() || playerTwo.hasCleanLine();
    }

    public void changeNext() {
        if (this.next.equals(PlayerTurn.ONE)) {
            this.next = PlayerTurn.TWO;
        } else {
            this.next = PlayerTurn.ONE;
        }
    }

    public void finishGame() {
        this.playerOne.finish();
        this.playerTwo.finish();
        this.finished = true;
    }

}
