package com.stolensugar.web.model;

import java.util.List;

import lombok.*;
import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class SpokenFormUser {
    @Getter @Setter @EqualsAndHashCode.Include private String id;
    @Getter @Setter private String spokenFormId;
    @Getter @Setter private String userId;
    @Getter @Setter private String choice;
    @Getter @Setter private String branchId;
    @Getter @Setter private List<String> choiceHistory;
    @Getter @Setter private Map<String, String> details;
    @Getter @Setter private Boolean pullRequestAvailable;
}
