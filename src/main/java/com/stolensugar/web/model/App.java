package com.stolensugar.web.model;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class App {
    @Getter @Setter @EqualsAndHashCode.Include private String name;
    @Getter @Setter private String url;
}
