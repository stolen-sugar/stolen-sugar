package com.stolensugar.web.model;

import java.util.List;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class SpokenForm {
    @Getter @Setter @EqualsAndHashCode.Include private String id;
    @Getter @Setter private String command;
    @Getter @Setter private String fileName;
    @Getter @Setter private String context;
    @Getter @Setter private String appName;
    @Getter @Setter private String defaultName;
    @Getter @Setter private List<String> alternatives;
}
