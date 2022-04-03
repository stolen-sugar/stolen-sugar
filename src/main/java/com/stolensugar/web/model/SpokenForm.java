package com.stolensugar.web.model;

import java.util.List;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SpokenForm {
    @Getter @Setter @EqualsAndHashCode.Include private String fullName;
    @Getter @Setter private String command;
    @Getter @Setter private String fileName;
    @Getter @Setter private String context;
    @Getter @Setter private String appName;
    @Getter @Setter private String defaultName;
    @Getter @Setter private List<String> alternatives;
}
