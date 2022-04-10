package com.bushy.fnbgame.controller.response;

import com.bushy.fnbgame.domain.constants.PlayerTurn;

public interface BoardProjection {

    String getId();

    SideProjection getPlayerOne();

    SideProjection getPlayerTwo();

    PlayerTurn getNext();

    boolean isFinished();
}
