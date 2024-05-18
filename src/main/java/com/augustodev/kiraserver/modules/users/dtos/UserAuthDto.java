package com.augustodev.kiraserver.modules.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserAuthDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
