package com.stackdevs.authenticationservice.controllers;

import com.stackdevs.authenticationservice.models.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> fetchAllUsers() {
        logger.info("\n===================== Received a request to fetch all users from the database ====================");
        List<User> users = List.of(
                new User(1L,"benson", "opisa", "benspnopisa@gmail.com", null, 2L, Collections.emptySet()),
                new User(2L, "jeremy", "doku", "jeremy.doku@gmancity.com",null,3L, Collections.emptySet())
        );

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
