package com.themoviedb.authenticator.service;

import com.themoviedb.authenticator.jwt.JwtService;
import com.themoviedb.authenticator.model.ListType;
import com.themoviedb.authenticator.model.MediaType;
import com.themoviedb.authenticator.model.dto.MediaDto;
import com.themoviedb.authenticator.model.entity.Media;
import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.model.entity.UserMedia;
import com.themoviedb.authenticator.model.exception.InvalidTokenException;
import com.themoviedb.authenticator.model.exception.MediaAlreadyExistInList;
import com.themoviedb.authenticator.model.exception.MediaNotFoundException;
import com.themoviedb.authenticator.model.exception.UserNotFoundException;
import com.themoviedb.authenticator.model.request.ListRequest;
import com.themoviedb.authenticator.model.response.CustomResponse;
import com.themoviedb.authenticator.repository.MediaRepository;
import com.themoviedb.authenticator.repository.UserMediaRepository;
import com.themoviedb.authenticator.service.utils.ListHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListService {

    private final JwtService jwtService;
    private final MediaRepository mediaRepository;
    private final UserMediaRepository userMediaRepository;
    private final ListHelpers listHelpers;

    public CustomResponse postInList(String authHeader, ListRequest listRequest, ListType listType)
            throws UserNotFoundException, InvalidTokenException, MediaAlreadyExistInList, MediaNotFoundException {

        final Integer  idMediaApi = listRequest.getIdMediaApi();
        final String mediaType = listRequest.getMediaType();

        // Obtengo el usuario mediante el token (Aca mismo se valida el token)
        User user = jwtService.getUserFromToken(authHeader);

        // Obtengo la entidad Media con el id del request
        Media media = mediaRepository.findByIdMedia(idMediaApi)
                .orElse(null);

        // Si la media no existe la creo
        if (media == null) {
            media = listHelpers.getMedia(authHeader, idMediaApi, mediaType);
            mediaRepository.save(media);
        }

        boolean inList = userMediaRepository.existsByUserAndMediaAndListType(user, media, listType);

        if (inList) throw new MediaAlreadyExistInList("Media already exist in list");

        // Creo la entidad UserMedia y guardo en BD
        UserMedia userMedia = UserMedia.builder()
                .user(user)
                .media(media)
                .listType(listType)
                .build();

        userMediaRepository.save(userMedia);

        // Mapeo la entidad MediaDTO
        MediaDto mediaDto = listHelpers.mediaToDto(media);

        return listHelpers.createResponseOk(mediaDto);
    }

    public CustomResponse getList(String authHeader, ListType listType)
            throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        // Obtengo el usuario mediante el token (Aca mismo se valida el token)
        User user = jwtService.getUserFromToken(authHeader);

        List<Media> listMedia = mediaRepository.findByUserAndListType(user, listType);

        if (listMedia.isEmpty()) throw new MediaNotFoundException("The list is empty");

        return listHelpers.createResponseOk(listHelpers.mediaListToDtoList(listMedia));
    }

    public CustomResponse<MediaDto> deleteInList(String authHeader, ListRequest listRequest, ListType listType)
            throws MediaNotFoundException, UserNotFoundException, InvalidTokenException {
        MediaType mediaType = MediaType.fromString(listRequest.getMediaType());

        // Validar token y obtener usuario
        User user = jwtService.getUserFromToken(authHeader);

        // Buscar media por su ID externo (idMediaApi)
        Media media = mediaRepository.findByIdMedia(listRequest.getIdMediaApi())
                .orElseThrow(() -> new MediaNotFoundException("Media not found: " + listRequest.getIdMediaApi()));

        if (media.getMediaType() != mediaType) {
            throw new MediaNotFoundException("Media not found: " + listRequest.getIdMediaApi());
        }

        // Buscar la relación UserMedia (media en la lista del usuario)
        Optional<UserMedia> userMediaOptional = userMediaRepository.findByUserAndMediaAndListType(user, media, listType);

        if (userMediaOptional.isEmpty()) {
            throw new MediaNotFoundException("La media no se encuentra en la lista del usuario.");
        }

        // Eliminar la relación
        userMediaRepository.delete(userMediaOptional.get());

        // Mapeo la entidad MediaDTO
        MediaDto mediaDto = listHelpers.mediaToDto(media);

        // Devolver respuesta
        return listHelpers.createResponseOk(mediaDto);
    }

}
