package com.stolensugar.web.model;

import java.util.List;

import lombok.*;
import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @ToString
@Builder
public class SpokenFormUser {
    @Getter @Setter @EqualsAndHashCode.Include private String userId;
    @Getter @Setter @EqualsAndHashCode.Include private String spokenFormFullName;
    @Getter @Setter private Map<String, Map<String, String>> choices;
}
