package com.themoviedb.media.dto;

import com.themoviedb.media.dto.credits.CastDto;
import com.themoviedb.media.dto.credits.CrewDto;
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

