package com.themoviedb.authenticator.service.utils;

import com.themoviedb.authenticator.client.MediaFeignClient;
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
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ListUtils {

    private final JwtService jwtService;
    private final MediaRepository mediaRepository;
    private final UserMediaRepository userMediaRepository;
    private final MediaFeignClient mediaFeignClient;

    public CustomResponse postInList(String authHeader, ListRequest listRequest, ListType listType)
            throws UserNotFoundException, InvalidTokenException, MediaAlreadyExistInList, MediaNotFoundException {

        // Obtengo el usuario mediante el token (Aca mismo se valida el token)
        User user = jwtService.getUserFromToken(authHeader);

        // Obtengo la entidad Media con el id del request
        Media media = mediaRepository.findByIdMedia(listRequest.getIdMediaApi())
                .orElse(null);

        // Si la media no existe la creo
        if (media == null) {
            media = getMedia(authHeader, listRequest.getIdMediaApi(), listRequest.getMediaType());
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
        MediaDto mediaDto = mediaToDto(media);

        CustomResponse<MediaDto> response = new CustomResponse<>();
        response.setStatus(200);
        response.setData(mediaDto);

        return response;
    }

    public CustomResponse getList(String authHeader, ListType listType) throws UserNotFoundException, InvalidTokenException, MediaNotFoundException {
        // Obtengo el usuario mediante el token (Aca mismo se valida el token)
        User user = jwtService.getUserFromToken(authHeader);

        List<Media> listMedia = mediaRepository.findByUserAndListType(user, listType);

        if (listMedia.isEmpty()) throw new MediaNotFoundException("The list is empty");

        CustomResponse<List<MediaDto>> response = new CustomResponse<>();
        response.setStatus(200);
        response.setData(mediaListToDtoList(listMedia));

        return response;
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
        MediaDto mediaDto = mediaToDto(media);

        // Devolver respuesta
        CustomResponse<MediaDto> response = new CustomResponse<>();
        response.setStatus(200);
        response.setData(mediaDto);

        return response;
    }

    private List<MediaDto> mediaListToDtoList(List<Media> favoriteList) {
        return favoriteList.stream()
                .map(this::mediaToDto)
                .toList();
    }

    private Media getMedia(String authHeader, Integer idMediaApi, String strMediaType) throws MediaNotFoundException {
        try {
            MediaType mediatype = MediaType.fromString(strMediaType);
            MediaDto mediaDto;

            switch (mediatype) {
                case MOVIE:
                    mediaDto = mediaFeignClient.getDetailsMovieFetch(
                            idMediaApi, "en", authHeader);
                    break;
                case SERIE:
                    mediaDto = mediaFeignClient.getDetailsTvFetch(
                            idMediaApi, "en", authHeader);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid media type: " + mediatype);
            }

            Media media = mediaDtoToEntity(mediaDto, mediatype);

            return media;
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new MediaNotFoundException("Media not found: " + idMediaApi);
            } else {
                throw new RuntimeException("Something went wrong: " + e.getMessage());
            }
        }
    }

    private MediaDto mediaToDto(Media media) {
        return MediaDto.builder()
                .id(media.getIdMedia())
                .title(media.getTitle())
                .overview(media.getOverview())
                .posterPath(media.getPosterPath())
                .mediaType(media.getMediaType())
                .releaseDate(media.getReleaseDate())
                .voteAverage(media.getVoteAverage())
                .build();
    }


    private Media mediaDtoToEntity(MediaDto mediaDto, MediaType mediaType) {
        return Media.builder()
                .idMedia(mediaDto.getId())
                .title(mediaDto.getTitle())
                .overview(mediaDto.getOverview())
                .mediaType(mediaType)
                .originalTitle(mediaDto.getOriginalTitle())
                .releaseDate(mediaDto.getReleaseDate())
                .posterPath(mediaDto.getPosterPath())
                .voteAverage(mediaDto.getVoteAverage())
                .build();
    }


}
