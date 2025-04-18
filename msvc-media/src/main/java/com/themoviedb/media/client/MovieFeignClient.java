package com.themoviedb.media.client;

import com.themoviedb.media.client.config.FeignClientConfig;
import com.themoviedb.media.dto.MovieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-media", url="${themoviedb.api.url}", configuration = FeignClientConfig.class)
public interface MovieFeignClient {

    @GetMapping(value = "/movie/popular", consumes = MediaType.APPLICATION_JSON_VALUE)
    MovieListDto getPopularMovie(@RequestParam Integer page, @RequestParam String language);
}
