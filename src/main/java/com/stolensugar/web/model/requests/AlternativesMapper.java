package com.stolensugar.web.model.requests;

import lombok.*;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class AlternativesMapper {
    @Setter @Getter private String fileName;
    @Setter @Getter private String action;
    @Setter @Getter private List<String> alternatives;
}