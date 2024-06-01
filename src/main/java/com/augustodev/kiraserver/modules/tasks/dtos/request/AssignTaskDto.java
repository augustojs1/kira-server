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
public class AssignTaskDto {
    private User currentUser;
    private Integer taskId;
    private Integer assignUserId;
}
