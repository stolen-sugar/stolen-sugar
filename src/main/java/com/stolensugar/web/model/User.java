package com.stolensugar.web.model;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class User {
    @Getter @Setter @EqualsAndHashCode.Include  private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String imageavatarUrl;
    @Getter @Setter private String talonUrl;
    @Getter @Setter private String cursorlessUrl;
    @Getter @Setter private String talonLastPush;
    @Getter @Setter private String cursorlessLastPush;
}
