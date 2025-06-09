package com.themoviedb.media.client;

import com.themoviedb.media.client.config.FeignClientConfig;
import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.movie.MovieDetailDto;
import com.themoviedb.media.dto.movie.MovieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-client", url="${themoviedb.api.url}", configuration = FeignClientConfig.class)
public interface MovieFeignClient {

    @GetMapping(value = "/movie/popular")
    MovieListDto getPopularMovieFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/movie/top_rated")
    MovieListDto getTopRatedMovieFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/movie/upcoming")
    MovieListDto getComingSoonMovieFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/search/movie")
    MovieListDto getSearchMovieFetch(@RequestParam Integer page, @RequestParam String language, @RequestParam String query);

    @GetMapping(value = "/movie/now_playing")
    MovieListDto getNowPlayingMovieFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/movie/{idMovie}")
    MovieDetailDto getDetailsMovieFetch(@PathVariable Integer idMovie, @RequestParam String language);

    @GetMapping(value = "/movie/{idMovie}/credits")
    CreditsDto getCreditsMovieFetch(@PathVariable Integer idMovie, @RequestParam String language);
}
