package com.augustodev.kiraserver.modules.boards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
public class BoardController {

    @GetMapping
    public ResponseEntity<String> getBoards() {
        return ResponseEntity.ok("Getting all boards");
    }
}
