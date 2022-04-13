package com.stolensugar.web.voiceCommands.mochiCards;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "content",
        "deck-id",
        "template-id",
        "fields"
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class MochiMapper {
    @JsonProperty("content")
    private @Getter @Setter String content;
    @JsonProperty("deck-id")
    private @Getter @Setter String deckId;
    @JsonProperty("template-id")
    private @Getter @Setter String templateId;
    @JsonProperty("fields")
    private @Getter @Setter Fields fields;
}