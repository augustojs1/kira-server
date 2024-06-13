package com.augustodev.kiraserver.modules.status.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangeStatusPositionDto {

    @NotNull(message = "boardId is required.")
    private Integer boardId;

    @NotNull(message = "statusOriginId is required.")
    private Integer statusOriginId;

    @NotNull(message = "statusOriginDestination is required.")
    private Integer statusOriginDestination;
}
