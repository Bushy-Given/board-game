package com.bushy.fnbgame.controller.request;

import com.bushy.fnbgame.domain.constants.PlayerTurn;
import com.bushy.fnbgame.service.PitService;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.With;

import static com.bushy.fnbgame.domain.constants.PlayerTurn.*;
import static com.bushy.fnbgame.domain.constants.PlayerTurn.ONE;

@With
@Data
@Getter
@Builder
public class Distributor {
    private NextPlay next;
    private Integer stones;

    public Distributor next() {
        return this.withNext(getUpdatedNext()).withStones(getUpdatedStones());
    }

    public NextPlay getUpdatedNext() {
        if (next.getIndex().equals(PitService.MAIN_BUCKET_INDEX)) {
            PlayerTurn turn = next.getPlayerTurn().equals(ONE) ? TWO : ONE;
            return NextPlay.builder().playerTurn(turn).index(0).build();
        } else {
            return next.withIndex(next.getIndex() + 1);
        }
    }

    public Integer getUpdatedStones() {
        return this.stones - 1;
    }
}
