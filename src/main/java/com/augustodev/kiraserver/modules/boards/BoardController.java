package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.dtos.request.CreateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.request.RemoveUserFromBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.request.UpdateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardCreatedDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardDTO;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.users.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardCreatedDto> postNewBoard(
            @Valid
            @RequestBody CreateBoardDto createBoardDto,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.createBoard(createBoardDto, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getBoards(
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.findAssociateBoards(user.getId()), HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public  ResponseEntity<UpdateBoardDto> updateBoard(
            @PathVariable Integer boardId,
            @Valid
            @RequestBody UpdateBoardDto updateBoardDto,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.update(boardId, updateBoardDto, user), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(
            @PathVariable Integer boardId,
            @AuthenticationPrincipal User user
    ) {
        this.boardService.delete(boardId, user);
    }

    @GetMapping("/{boardId}/users")
    public ResponseEntity<List<User>> getUsersFromBoard(
            @PathVariable Integer boardId,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.findUsers(boardId, user), HttpStatus.OK);
    }

    @DeleteMapping("{boardId}/remove/{userId}")
    public ResponseEntity deleteUserFromBoard(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        this.boardService.removeUser(new RemoveUserFromBoardDto(user.getId(), userId, boardId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{boardId}/users/{userId}/admin")
    public ResponseEntity<BoardMembers> changeUserRole(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        return new ResponseEntity<>(
                this.boardService.userToAdmin(new RemoveUserFromBoardDto(user.getId(), userId, boardId)),
                HttpStatus.NO_CONTENT
        );
    }
}
