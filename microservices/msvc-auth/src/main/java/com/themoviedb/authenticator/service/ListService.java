package com.themoviedb.authenticator.service;

import com.themoviedb.authenticator.model.*;
import com.themoviedb.authenticator.model.exception.InvalidTokenException;
import com.themoviedb.authenticator.model.exception.MediaAlreadyExistInList;
import com.themoviedb.authenticator.model.exception.MediaNotFoundException;
import com.themoviedb.authenticator.model.exception.UserNotFoundException;
import com.themoviedb.authenticator.model.request.ListRequest;
import com.themoviedb.authenticator.model.response.CustomResponse;
import com.themoviedb.authenticator.service.utils.ListUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListUtils listUtils;

    // Favorite
    public CustomResponse postFavoriteList(String authHeader, ListRequest listRequest)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException, MediaAlreadyExistInList {
        return listUtils.postInList(authHeader, listRequest, ListType.FAVORITE);
    }

    public CustomResponse getFavoriteList(String authHeader)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return listUtils.getList(authHeader, ListType.FAVORITE);
    }

    public CustomResponse deleteFavoriteList(String authHeader, ListRequest listRequest)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return listUtils.deleteInList(authHeader, listRequest, ListType.FAVORITE);
    }

    // Watchlist
    public CustomResponse postWatchList(String authHeader, ListRequest listRequest)
            throws UserNotFoundException, InvalidTokenException, MediaAlreadyExistInList, MediaNotFoundException {
        return listUtils.postInList(authHeader, listRequest, ListType.WATCHLIST);
    }

    public CustomResponse getWatchList(String authHeader)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return listUtils.getList(authHeader, ListType.WATCHLIST);
    }

    public CustomResponse deleteWatchList(String authHeader, ListRequest listRequest)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        return listUtils.deleteInList(authHeader, listRequest, ListType.WATCHLIST);
    }
}
