package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.dtos.BoardCreatedDto;
import com.augustodev.kiraserver.modules.boards.dtos.CreateBoardDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardCreatedDto> postNewBoard(
            @RequestBody CreateBoardDto createBoardDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.boardService.createBoard(createBoardDto, user), HttpStatus.CREATED);
    }
}
