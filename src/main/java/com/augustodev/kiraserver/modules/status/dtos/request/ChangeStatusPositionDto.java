package com.augustodev.kiraserver.modules.status.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangeStatusPositionDto {
    private Integer boardId;
    private Integer statusOriginId;
    private Integer statusOriginDestination;
}
