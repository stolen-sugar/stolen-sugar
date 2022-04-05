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
    @Getter @Setter private String lastUpdated;
    @Getter @Setter private String app;
    @Getter @Setter private String repo;
    @Getter @Setter private String branch;
    @Getter @Setter private String choice;
}
