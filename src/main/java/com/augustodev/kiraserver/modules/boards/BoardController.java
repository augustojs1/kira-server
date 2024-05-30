package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.dtos.*;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<BoardDTO>> getBoards(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.boardService.findAssociateBoards(user.getId()), HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public  ResponseEntity<UpdateBoardDto> updateBoard(
            @PathVariable Integer boardId,
            @RequestBody UpdateBoardDto updateBoardDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.boardService.update(boardId, updateBoardDto, user), HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public void deleteBoard(
            @PathVariable Integer boardId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;

        this.boardService.delete(boardId, user);
    }

    @GetMapping("/{boardId}/users")
    public ResponseEntity<List<User>> getUsersFromBoard(
            @PathVariable Integer boardId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.boardService.findUsers(boardId, user), HttpStatus.OK);
    }

    @DeleteMapping("{boardId}/remove/{userId}")
    public ResponseEntity deleteUserFromBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        User user = (User) userDetails;

        this.boardService.removeUser(new RemoveUserFromBoardDto(user.getId(), userId, boardId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{boardId}/users/{userId}/admin")
    public ResponseEntity<BoardMembers> changeUserRole(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(
                this.boardService.userToAdmin(new RemoveUserFromBoardDto(user.getId(), userId, boardId)),
                HttpStatus.NO_CONTENT
        );
    }
}
