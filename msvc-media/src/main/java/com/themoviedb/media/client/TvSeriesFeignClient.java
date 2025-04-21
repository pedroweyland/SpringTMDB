package com.themoviedb.media.client;

import com.themoviedb.media.client.config.FeignClientConfig;
import com.themoviedb.media.dto.SerieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tv-series-client", url="${themoviedb.api.url}", configuration = FeignClientConfig.class)
public interface TvSeriesFeignClient {

    @GetMapping(value = "/tv/popular")
    SerieListDto getPopularSeries(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/tv/top_rated")
    SerieListDto getTopRatedSeries(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/tv/on_the_air")
    SerieListDto getOnTheAirSeries(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/tv/airing_today")
    SerieListDto getAiringTodaySeries(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/search/tv")
    SerieListDto getSearchSeries(@RequestParam Integer page, @RequestParam String language, @RequestParam String query);

}
