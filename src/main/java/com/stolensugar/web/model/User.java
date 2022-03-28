package com.stolensugar.web.model;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class User {
    @Getter @Setter @EqualsAndHashCode.Include  private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String imageavatarUrl;
    @Getter @Setter private String reposUrl;

}
