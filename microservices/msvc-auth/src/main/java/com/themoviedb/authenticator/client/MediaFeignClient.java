package com.themoviedb.authenticator.client;

import com.themoviedb.authenticator.client.config.FeignClientConfig;
import com.themoviedb.authenticator.model.dto.MediaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-media", url = "${msvc.api.url}", configuration = FeignClientConfig.class)
public interface MediaFeignClient {

    @GetMapping(value = "api/v1/media/movie/details/{idMovie}")
    MediaDto getDetailsMovieFetch(
            @PathVariable Integer idMovie,
            @RequestParam(defaultValue = "en")  String language,
            @RequestHeader("Authorization") String token);

    @GetMapping(value = "api/v1/media/tv/details/{idTv}")
    MediaDto getDetailsTvFetch(@PathVariable Integer idTv,
                            @RequestParam(defaultValue = "en") String language,
                            @RequestHeader("Authorization") String token);
}
