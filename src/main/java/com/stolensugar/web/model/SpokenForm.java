package com.stolensugar.web.model;

import java.util.Set;

import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SpokenForm {
    @Getter @Setter @EqualsAndHashCode.Include private String fileName;
    @Getter @Setter @EqualsAndHashCode.Include private String action;
    @Getter @Setter private String context;
    @Getter @Setter private String appName;
    @Getter @Setter private String defaultName;
    @Getter @Setter private Set<String> alternatives;
}
