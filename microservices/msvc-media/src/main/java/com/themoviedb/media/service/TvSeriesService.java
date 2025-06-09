package com.themoviedb.media.service;

import com.themoviedb.media.client.TvSeriesFeignClient;
import com.themoviedb.media.dto.credits.CreditsDto;
import com.themoviedb.media.dto.serie.SerieDetailDto;
import com.themoviedb.media.dto.serie.SerieListDto;
import com.themoviedb.media.exception.MediaNotFoundException;
import com.themoviedb.media.service.utils.AbstractMediaService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TvSeriesService extends AbstractMediaService<SerieListDto> implements IMediaService<SerieListDto> {

    private final TvSeriesFeignClient tvSeriesFeign;

    @Override
    public SerieListDto getPopularMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    SerieListDto serieList = tvSeriesFeign.getPopularSeriesFetch(page, language.toLowerCase());
                    validatePageContent(serieList.getTotalPages(), page);
                    return serieList;
                },
                page
        );
    }

    @Override
    public SerieListDto getTopRatedMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    SerieListDto serieList = tvSeriesFeign.getTopRatedSeriesFetch(page, language.toLowerCase());
                    validatePageContent(serieList.getTotalPages(), page);
                    return serieList;
                },
                page
        );
    }

    @Override
    public SerieListDto getComingSoonMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    SerieListDto serieList = tvSeriesFeign.getOnTheAirSeriesFetch(page, language.toLowerCase());
                    validatePageContent(serieList.getTotalPages(), page);
                    return serieList;
                },
                page
        );
    }

    @Override
    public SerieListDto getSearchMedia(Integer page, String language, String query) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    SerieListDto serieList = tvSeriesFeign.getSearchSeriesFetch(page, language.toLowerCase(), query);
                    validateSearchQuery(serieList.getResults().isEmpty(), query);
                    return serieList;
                },
                page
        );
    }

    @Override
    public SerieListDto getNowPlayingMedia(Integer page, String language) {
        validateLanguage(language);

        return executeWithHandling(
                () -> {
                    SerieListDto serieList = tvSeriesFeign.getAiringTodaySeriesFetch(page, language.toLowerCase());
                    validatePageContent(serieList.getTotalPages(), page);
                    return serieList;
                },
                page
        );
    }

    public SerieDetailDto getDetailsSerie(Integer idSerie, String language) {
        try {
            validateLanguage(language);

            return tvSeriesFeign.getDetailsSeriesFetch(idSerie, language.toLowerCase());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new MediaNotFoundException("Serie not found: " + idSerie);
            }
            throw new RuntimeException("Something went wrong while fetching popular series: " + e.getMessage());
        }
    }

    public CreditsDto getCreditsSerie(Integer idSerie, String language) {
        try {
            validateLanguage(language);

            return tvSeriesFeign.getCreditsSeriesFetch(idSerie, language.toLowerCase());
        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new MediaNotFoundException("Serie not found: " + idSerie);
            }
            throw new RuntimeException("Something went wrong while fetching popular series: " + e.getMessage());
        }
    }

}
