package com.augustodev.kiraserver.modules.invites.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSlimDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
