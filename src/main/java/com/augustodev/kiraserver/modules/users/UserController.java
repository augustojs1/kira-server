package com.augustodev.kiraserver.modules.users;

import com.augustodev.kiraserver.modules.users.dtos.UserAuthDto;
import com.augustodev.kiraserver.modules.users.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserAuthDto> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;

        UserAuthDto currentUser = UserAuthDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

        return ResponseEntity.ok(currentUser);
    }
}
