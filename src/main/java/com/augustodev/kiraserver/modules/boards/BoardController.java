package com.augustodev.kiraserver.modules.boards;

import com.augustodev.kiraserver.modules.boards.dtos.request.CreateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.request.RemoveUserFromBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.request.UpdateBoardDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardCreatedDto;
import com.augustodev.kiraserver.modules.boards.dtos.response.BoardDTO;
import com.augustodev.kiraserver.modules.boards.entities.BoardMembers;
import com.augustodev.kiraserver.modules.users.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Boards", description = "Collection of REST API endpoints for board management.")
public class BoardController {

    private final BoardService boardService;

    @Operation(
            description = "Create a new board.",
            summary = "Create a new board with admin role.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Board with the same name already exists",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BoardCreatedDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<BoardCreatedDto> postNewBoard(
            @Valid
            @RequestBody CreateBoardDto createBoardDto,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.createBoard(createBoardDto, user), HttpStatus.CREATED);
    }

    @Operation(
            description = "Get a list of boards the current user is a member.",
            summary = "Get a list of boards the current user is a member.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BoardDTO.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @GetMapping
    public ResponseEntity<List<BoardDTO>> getBoards(
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.findAssociateBoards(user.getId()), HttpStatus.OK);
    }

    @Operation(
            description = "Update board information in case current user is board admin.",
            summary = "Update board information in case current user is board admin.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateBoardDto.class
                                            )
                                    ),
                            }
                    ),
            }
    )
    @PutMapping("/{boardId}")
    public  ResponseEntity<UpdateBoardDto> updateBoard(
            @PathVariable Integer boardId,
            @Valid
            @RequestBody UpdateBoardDto updateBoardDto,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.update(boardId, updateBoardDto, user), HttpStatus.OK);
    }

    @Operation(
            description = "Delete a board if current user is board admin.",
            summary = "Delete a board if current user is board admin.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    ),
            }
    )
    @DeleteMapping("/{boardId}")
    public void deleteBoard(
            @PathVariable Integer boardId,
            @AuthenticationPrincipal User user
    ) {
        this.boardService.delete(boardId, user);
    }

    @Operation(
            description = "Get a list of members associated to a board.",
            summary = "Get a list of members associated to a board.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Board not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
            }
    )
    @GetMapping("/{boardId}/users")
    public ResponseEntity<List<User>> getUsersFromBoard(
            @PathVariable Integer boardId,
            @AuthenticationPrincipal User user
    ) {
        return new ResponseEntity<>(this.boardService.findUsers(boardId, user), HttpStatus.OK);
    }

    @Operation(
            description = "Remove a member from a board as a board admin.",
            summary = "Remove a member from a board as a board admin.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Board not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    ),
            }
    )
    @DeleteMapping("{boardId}/remove/{userId}")
    public ResponseEntity deleteUserFromBoard(
            @AuthenticationPrincipal User user,
            @PathVariable Integer boardId,
            @PathVariable Integer userId
    ) {
        this.boardService.removeUser(new RemoveUserFromBoardDto(user.getId(), userId, boardId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Change a board member role.",
            summary = "Change a board member role.",
            responses = {
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Invalid request body",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Board not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    ),
            }
    )
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
