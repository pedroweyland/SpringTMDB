package com.themoviedb.media.client;

import com.themoviedb.media.client.config.FeignClientConfig;
import com.themoviedb.media.dto.CreditsDto;
import com.themoviedb.media.dto.MovieDetailDto;
import com.themoviedb.media.dto.MovieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-client", url="${themoviedb.api.url}", configuration = FeignClientConfig.class)
public interface MovieFeignClient {

    @GetMapping(value = "/movie/popular")
    MovieListDto getPopularMovie(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/movie/{idMovie}")
    MovieDetailDto getDetailsMovie(@PathVariable Integer idMovie, @RequestParam String language);

    @GetMapping(value = "/movie/top_rated")
    MovieListDto getTopRatedMovie(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/movie/upcoming")
    MovieListDto getComingSoonMovie(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/search/movie")
    MovieListDto getSearchMovie(@RequestParam Integer page, @RequestParam String language, @RequestParam String query);

    @GetMapping(value = "/movie/now_playing")
    MovieListDto getNowPlayingMovie(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/movie/{idMovie}/credits")
    CreditsDto getCreditsMovie(@PathVariable Integer idMovie, @RequestParam String language);
}
