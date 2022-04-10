package com.bushy.fnbgame.service;

import com.bushy.fnbgame.controller.request.Distributor;
import com.bushy.fnbgame.controller.request.NextPlay;
import com.bushy.fnbgame.controller.request.PlayRequest;
import com.bushy.fnbgame.domain.Board;
import com.bushy.fnbgame.domain.Line;
import com.bushy.fnbgame.domain.Pit;
import com.bushy.fnbgame.domain.constants.PlayerTurn;
import org.springframework.stereotype.Service;

import static com.bushy.fnbgame.domain.constants.PlayerTurn.ONE;
import static com.bushy.fnbgame.domain.constants.PlayerTurn.TWO;

@Service
public class PitService {

    public static final Integer MAIN_BUCKET_INDEX = 6;
    public static final Integer FIRST_PIT_INDEX = 0;

    public static Integer clearPitAndGetStones(PlayRequest request, Board board) {
        Pit pit = getPit(request, board);

        Integer stones = pit.getStones();
        pit.setStones(0);
        return stones;
    }

    public static Pit getPit(NextPlay nextPlay, Board board) {
        if (ONE.equals(nextPlay.getPlayerTurn())) {
            return MAIN_BUCKET_INDEX.equals(nextPlay.getIndex()) ?
                    board.getPlayerOne().getMainBucket() :
                    getPit(board.getPlayerOne(), nextPlay.getIndex());
        } else {
            return MAIN_BUCKET_INDEX.equals(nextPlay.getIndex()) ?
                    board.getPlayerTwo().getMainBucket() :
                    getPit(board.getPlayerTwo(), nextPlay.getIndex());
        }
    }

    public static Pit getPit(PlayRequest request, Board board) {
        return PlayerTurn.ONE.equals(request.getPlayerTurn()) ?
                getPit(board.getPlayerOne(), request.getPitIndex()) :
                getPit(board.getPlayerTwo(), request.getPitIndex());
    }

    private static Pit getPit(Line playerOne, Integer pitIndex) {
        return playerOne.getPits().get(pitIndex);
    }

    public static Integer captureStones(Board board, Distributor distributor) {
        Pit mirrored = getMirrored(distributor, board);
        Integer stones = mirrored.getStones() + 1;
        mirrored.setStones(0);

        return stones;
    }

    private static Pit getMirrored(Distributor distributor, Board board) {
        if (ONE.equals(distributor.getNext().getPlayerTurn())) {
            return getPit(
                    NextPlay.builder()
                            .index(PitService.getMirroredIndex(distributor.getNext().getIndex()))
                            .playerTurn(TWO)
                            .build(),
                    board);
        } else {
            return getPit(
                    NextPlay.builder()
                            .index(PitService.getMirroredIndex(distributor.getNext().getIndex()))
                            .playerTurn(PlayerTurn.ONE)
                            .build(),
                    board);
        }
    }

    private static Integer getMirroredIndex(Integer index) {
        return 5 - index;
    }

}
