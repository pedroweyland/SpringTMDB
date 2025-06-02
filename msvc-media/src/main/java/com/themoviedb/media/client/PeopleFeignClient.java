package com.themoviedb.media.client;

import com.themoviedb.media.client.config.FeignClientConfig;
import com.themoviedb.media.dto.people.credits.CreditsPeopleDto;
import com.themoviedb.media.dto.people.PeopleListDto;
import com.themoviedb.media.dto.people.PersonDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "people-client", url="${themoviedb.api.url}", configuration = FeignClientConfig.class)
public interface PeopleFeignClient {

    @GetMapping(value = "person/popular")
    PeopleListDto getPopularPeopleFetch(@RequestParam Integer page, @RequestParam String language);

    @GetMapping(value = "person/{idPerson}")
    PersonDetailDto getPersonDetailFetch(@PathVariable Integer idPerson, @RequestParam String language);

    @GetMapping(value = "person/{idPerson}/movie_credits")
    CreditsPeopleDto getCreditsPersonMovieFetch(@PathVariable Integer idPerson, @RequestParam String lowerCase);

    @GetMapping(value = "person/{idPerson}/tv_credits")
    CreditsPeopleDto getCreditsPersonTvShowFetch(@PathVariable Integer idPerson, @RequestParam String lowerCase);
}
