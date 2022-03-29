package com.stolensugar.web.model;

import java.time.OffsetDateTime;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Branch {
    @Setter @Getter @EqualsAndHashCode.Include private String id;
    @Setter @Getter private String name;
    @Setter @Getter private String url;
    @Setter @Getter private String repoId;
    @Setter @Getter private String primary;
    @Setter @Getter private OffsetDateTime lassUpdated;
}
