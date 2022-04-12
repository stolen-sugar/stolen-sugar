package com.stolensugar.web.voiceCommands.mochiCards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class MochiMapper {
    private @Getter @Setter String content;
    private @Getter @Setter String deckId;
    private @Getter @Setter String templateId;
    private @Getter @Setter Map<String, String> action;
    private @Getter @Setter Map<String, String> choice;
    private @Getter @Setter Map<String, String> context;
    private @Getter @Setter Map<String, String> file;
}