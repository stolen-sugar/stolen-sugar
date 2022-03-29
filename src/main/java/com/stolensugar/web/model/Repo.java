package com.stolensugar.web.model;

import java.util.Objects;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Repo {
    @Getter @Setter @EqualsAndHashCode.Include private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String appName;
    @Getter @Setter private String url;
    @Getter @Setter private Boolean primary;
    @Getter @Setter private String userId;
}
