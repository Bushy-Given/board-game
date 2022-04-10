package com.bushy.fnbgame.controller.request;

import com.bushy.fnbgame.domain.constants.PlayerTurn;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.With;

@With
@Data
@Getter
@Builder
public class NextPlay {
    private Integer index;
    private PlayerTurn playerTurn;
}
