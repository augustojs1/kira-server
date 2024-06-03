package com.augustodev.kiraserver.modules.tasks.dtos.request;

import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangeTaskStatusDto {
    private User user;
    private Integer boardId;
    private Integer taskId;
    private Integer statusId;
}
