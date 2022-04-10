package com.bushy.fnbgame.controller;

import com.bushy.fnbgame.controller.request.PlayRequest;
import com.bushy.fnbgame.controller.response.BoardProjection;
import com.bushy.fnbgame.domain.Board;
import com.bushy.fnbgame.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "boards")
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final SpelAwareProxyProjectionFactory projectionFactory;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardProjection create() {
        return projectionFactory.createProjection(BoardProjection.class, boardService.create());
    }

    @GetMapping(path = "/{id}")
    public BoardProjection findById(@PathVariable String id) {
        return projectionFactory.createProjection(BoardProjection.class, boardService.findById(id));
    }

    @PutMapping(path = "/{id}")
    public BoardProjection play(@PathVariable String id,
                              @Valid @RequestBody PlayRequest request) {
        return projectionFactory.createProjection(BoardProjection.class, boardService.play(request.withId(id)));
    }
}
