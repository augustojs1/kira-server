package com.augustodev.kiraserver.modules.status;

import com.augustodev.kiraserver.modules.status.dtos.request.CreateStatusDto;
import com.augustodev.kiraserver.modules.status.dtos.response.CreateStatusResponseDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<CreateStatusResponseDto> create(
                @AuthenticationPrincipal UserDetails userDetails,
                @RequestBody CreateStatusDto createStatusDto
            ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.statusService.create(createStatusDto, user.getId()), HttpStatus.CREATED);
    }
}
