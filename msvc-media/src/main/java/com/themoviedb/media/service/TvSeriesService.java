package com.themoviedb.media.service;

import com.themoviedb.media.client.TvSeriesFeignClient;
import com.themoviedb.media.dto.SerieListDto;
import com.themoviedb.media.exception.PageNotFoundException;
import com.themoviedb.media.service.utils.AbstractMediaService;
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
                    SerieListDto seriesList = tvSeriesFeign.getPopularSeries(page, language.toLowerCase());
                    validatePageContent(seriesList, page, null);
                    return seriesList;
                },
                "Page not found: " + page
        );
    }

    @Override
    public SerieListDto getTopRatedMedia(Integer page, String language) {
        return null;
    }

    @Override
    public SerieListDto getComingSoonMedia(Integer page, String language) {
        return null;
    }

    @Override
    public SerieListDto getSearchMedia(Integer page, String language, String query) {
        return null;
    }

    @Override
    public SerieListDto getNowPlayingMedia(Integer page, String language) {
        return null;
    }

    private void validatePageContent(SerieListDto serieList, Integer page, String query) {

        if (serieList.getTotalPages() < page) {
            throw new PageNotFoundException("Page not found: " + page);
        }

        if (serieList.getResults().isEmpty()) {
            throw new IllegalArgumentException("No results found" + (query != null ? " for query: " + query : ""));
        }
    }
}
