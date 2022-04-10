package com.bushy.fnbgame.service;

import com.bushy.fnbgame.controller.request.Distributor;
import com.bushy.fnbgame.controller.request.NextPlay;
import com.bushy.fnbgame.controller.request.PlayRequest;
import com.bushy.fnbgame.domain.Board;
import com.bushy.fnbgame.domain.Pit;
import com.bushy.fnbgame.domain.constants.PlayerTurn;
import com.bushy.fnbgame.repository.BoardRepository;
import com.bushy.fnbgame.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board create() {
        return boardRepository.save(new Board());
    }

    public Board findById(final String id) {
        return load(id);
    }

    public Board play(final PlayRequest request) {
        Board board = load(request.getId());

        validatePlay(request, board);

        Distributor parameter = Distributor.builder()
                .next(NextPlay.builder()
                        .index(request.getPitIndex() + 1)
                        .playerTurn(request.getPlayerTurn())
                        .build())
                .stones(PitService.clearPitAndGetStones(request, board))
                .build();

        return boardRepository.save(distributeStones(board, parameter));
    }

    private Board distributeStones(Board board, Distributor distributor) {
        if (distributor.getStones() > 0) {
            updatePitStones(board, distributor);
            return distributeStones(board, distributor.next());
        }

        if (endedInMainBucket(distributor)) {
            board.changeNext();
        }

        if (board.hasCleanLine()) {
            board.finishGame();
        }

        return board;
    }

    private boolean endedInMainBucket(Distributor distributor) {
        return !PitService.FIRST_PIT_INDEX.equals(distributor.getNext().getIndex());
    }

    private void updatePitStones(Board board, Distributor distributor) {
        Pit pit = PitService.getPit(distributor.getNext(), board);

        if (distributor.getStones() == 1 && pit.getStones().equals(0) && !pit.isMainBucket()) {
            pit.setStones(PitService.captureStones(board, distributor));
        } else {
            pit.setStones(pit.getStones() + 1);
        }
    }

    private Board load(final String id) {
        return boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
    }

    private void validatePlay(PlayRequest request, Board board) {
        validateGameFinished(board);
        validatePlayer(board, request.getPlayerTurn());
        validateMainBucket(request);
        validatePitStones(board, request);
    }

    private void validateMainBucket(PlayRequest request) {
        if (request.getPitIndex().equals(PitService.MAIN_BUCKET_INDEX)) {
            throw new IllegalPlayException();
        }
    }

    private void validatePitStones(Board board, PlayRequest request) {
        if (PitService.getPit(request, board).getStones() < 1) {
            throw new EmptyPitException();
        }
    }

    private void validatePlayer(Board board, PlayerTurn playerTurn) {
        if (!board.getNext().equals(playerTurn)) {
            throw new WrongPlayException();
        }
    }

    private void validateGameFinished(Board board) {
        if (board.isFinished()) {
            throw new GameFinishedException();
        }
    }
}
