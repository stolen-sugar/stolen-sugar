package com.stolensugar.web.model.requests;

import lombok.*;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class UpdateItemAlternativesRequest {
    @Setter @Getter private List<AlternativesMapper> alternativesMapperList;
}