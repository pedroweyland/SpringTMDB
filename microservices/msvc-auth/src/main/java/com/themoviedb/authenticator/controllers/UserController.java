package com.themoviedb.authenticator.controllers;

import com.themoviedb.authenticator.model.exception.InvalidTokenException;
import com.themoviedb.authenticator.model.exception.InvalidUserDataException;
import com.themoviedb.authenticator.model.exception.UserNotFoundException;
import com.themoviedb.authenticator.service.UserService;
import com.themoviedb.authenticator.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws InvalidTokenException, UserNotFoundException {
        return ResponseEntity.ok(userService.getCurrentUser(authHeader));
    }

    @PutMapping(value = "update")
    public ResponseEntity<UserDto> updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody UserDto userDto) throws InvalidTokenException, UserNotFoundException, InvalidUserDataException {
        return ResponseEntity.ok(userService.updateUser(authHeader, userDto));
    }


}
