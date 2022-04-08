package com.stolensugar.web.controller.mappers;

import java.util.List;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SpokenFormAlternatives {
    @Getter @Setter private String fileName;
    @Getter @Setter private String action;
    @Getter @Setter private List<String> alternatives;
}
