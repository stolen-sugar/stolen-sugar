package com.stolensugar.web.model.requests;

import lombok.*;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@ToString
@Builder
public class AlternativesMapper {
    @Setter @Getter private String fileName;
    @Setter @Getter private String action;
    @Setter @Getter private Set<String> alternatives;
}