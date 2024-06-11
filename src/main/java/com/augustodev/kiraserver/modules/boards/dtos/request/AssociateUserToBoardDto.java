package com.augustodev.kiraserver.modules.boards.dtos.request;

import com.augustodev.kiraserver.modules.users.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AssociateUserToBoardDto {
    private Integer userId;
    private Integer boardId;
    private Role role;
}
