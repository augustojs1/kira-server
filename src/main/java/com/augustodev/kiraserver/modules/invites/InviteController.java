package com.augustodev.kiraserver.modules.invites;

import com.augustodev.kiraserver.modules.invites.dtos.InviteUserDto;
import com.augustodev.kiraserver.modules.invites.dtos.UserInvitesDto;
import com.augustodev.kiraserver.modules.invites.entities.Invite;
import com.augustodev.kiraserver.modules.users.entities.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<Invite> invite(
            @RequestBody InviteUserDto inviteUserDto,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
            User user = (User) userDetails;

            return new ResponseEntity<>(this.inviteService.invite(user, inviteUserDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserInvitesDto>> getInvites(
            @AuthenticationPrincipal User user
    ) {

        return new ResponseEntity<>(this.inviteService.findInvitesById(user.getId()), HttpStatus.OK);
    }

    @GetMapping("/sent")
    public ResponseEntity<List<UserInvitesDto>> getSentInvites(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = (User) userDetails;

        return new ResponseEntity<>(this.inviteService.findSentInvites(user.getId()), HttpStatus.OK);
    }

    @PostMapping("/accept/{inviteId}")
    public ResponseEntity acceptInvite(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer inviteId
    ) {
        this.inviteService.accept(inviteId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/deny/{inviteId}")
    public ResponseEntity denyInvite(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer inviteId
    ) {
        this.inviteService.deny(inviteId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
