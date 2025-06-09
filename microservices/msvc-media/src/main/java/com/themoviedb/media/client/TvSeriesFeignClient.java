package com.themoviedb.media.client;

import com.themoviedb.media.client.config.FeignClientConfig;
import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.serie.SerieDetailDto;
import com.themoviedb.media.dto.serie.SerieListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tv-series-client", url="${themoviedb.api.url}", configuration = FeignClientConfig.class)
public interface TvSeriesFeignClient {

    @GetMapping(value = "/tv/popular")
    SerieListDto getPopularSeriesFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/tv/top_rated")
    SerieListDto getTopRatedSeriesFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/tv/on_the_air")
    SerieListDto getOnTheAirSeriesFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/tv/airing_today")
    SerieListDto getAiringTodaySeriesFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "/search/tv")
    SerieListDto getSearchSeriesFetch(@RequestParam Integer page, @RequestParam String language, @RequestParam String query);

    @GetMapping(value = "/tv/{idSerie}")
    SerieDetailDto getDetailsSeriesFetch(@PathVariable Integer idSerie, @RequestParam String language);

    @GetMapping(value = "/tv/{idSerie}/credits")
    CreditsDto getCreditsSeriesFetch(@PathVariable Integer idSerie, @RequestParam String language);
}
