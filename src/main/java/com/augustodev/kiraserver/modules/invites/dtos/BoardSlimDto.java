package com.augustodev.kiraserver.modules.invites.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class BoardSlimDto {
    private Integer id;
    private String title;
    private String description;
}
