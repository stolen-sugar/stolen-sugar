package com.stolensugar.web.voiceCommands.mochiCards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Choice123 {
    private @Getter @Setter String id;
    private @Getter @Setter String value;
}