package com.bushy.fnbgame.controller.request;

import com.bushy.fnbgame.domain.constants.PlayerTurn;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
@With
@Data
@Builder
public class PlayRequest {

    private String id;

    private PlayerTurn playerTurn;

    @Max(5)
    @PositiveOrZero
    private Integer pitIndex;

}
