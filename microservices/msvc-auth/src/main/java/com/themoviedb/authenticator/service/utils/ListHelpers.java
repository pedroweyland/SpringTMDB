package com.themoviedb.authenticator.service.utils;

import com.themoviedb.authenticator.client.MediaFeignClient;
import com.themoviedb.authenticator.model.MediaType;
import com.themoviedb.authenticator.model.dto.MediaDto;
import com.themoviedb.authenticator.model.entity.Media;
import com.themoviedb.authenticator.model.exception.MediaNotFoundException;
import com.themoviedb.authenticator.model.response.CustomResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListHelpers {

    private final MediaFeignClient mediaFeignClient;

    public List<MediaDto> mediaListToDtoList(List<Media> favoriteList) {
        return favoriteList.stream()
                .map(this::mediaToDto)
                .toList();
    }

    public Media getMedia(String authHeader, Integer idMediaApi, String strMediaType) throws MediaNotFoundException {
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

    public MediaDto mediaToDto(Media media) {
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

    public Media mediaDtoToEntity(MediaDto mediaDto, MediaType mediaType) {
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

    public <T> CustomResponse<T> createResponseOk(T data) {
        CustomResponse<T> response = new CustomResponse<>();
        response.setStatus(200);
        response.setData(data);
        return response;
    }
}
