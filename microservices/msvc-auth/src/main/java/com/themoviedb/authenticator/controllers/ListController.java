package com.themoviedb.authenticator.controllers;

import com.themoviedb.authenticator.model.exception.InvalidTokenException;
import com.themoviedb.authenticator.model.exception.MediaAlreadyExistInList;
import com.themoviedb.authenticator.model.exception.MediaNotFoundException;
import com.themoviedb.authenticator.model.exception.UserNotFoundException;
import com.themoviedb.authenticator.model.request.ListRequest;
import com.themoviedb.authenticator.model.response.CustomResponse;
import com.themoviedb.authenticator.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/lists")
@RequiredArgsConstructor
public class ListController {

    private final ListService listService;

    @PostMapping(value = "favorite")
    public ResponseEntity<CustomResponse> postFavoriteList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ListRequest listRequest)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException, MediaAlreadyExistInList {
        return ResponseEntity.ok(listService.postFavoriteList(authHeader, listRequest));
    }

    @GetMapping(value = "favorite")
    public ResponseEntity<CustomResponse> getFavoriteList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return ResponseEntity.ok(listService.getFavoriteList(authHeader));
    }

    @DeleteMapping(value = "favorite")
    public ResponseEntity<CustomResponse> deleteFavoriteList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ListRequest listRequest) throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return ResponseEntity.ok(listService.deleteFavoriteList(authHeader, listRequest));
    }

    @PostMapping(value = "watchlist")
    public ResponseEntity<CustomResponse> postWatchList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ListRequest listRequest)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException, MediaAlreadyExistInList {
        return ResponseEntity.ok(listService.postWatchList(authHeader, listRequest));
    }

    @GetMapping(value = "watchlist")
    public ResponseEntity<CustomResponse> getWatchList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return ResponseEntity.ok(listService.getWatchList(authHeader));
    }

    @DeleteMapping(value = "watchlist")
    public ResponseEntity<CustomResponse> deleteWatchList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody ListRequest listRequest) throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return ResponseEntity.ok(listService.deleteWatchList(authHeader, listRequest));
    }

}


