package com.themoviedb.media.dto.credits;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreditsDto {

    private Integer id;
    private List<CastDto> cast;
    private List<CrewDto> crew;
}

