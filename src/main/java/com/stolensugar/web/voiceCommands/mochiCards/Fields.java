package com.stolensugar.web.voiceCommands.mochiCards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "choice"
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Fields {
    @JsonProperty("name")
    private @Getter @Setter
    FieldParams name;
    @JsonProperty("jvjwdOZ1")
    private @Getter @Setter
    FieldParams choice;
    @JsonProperty("Spe228xW")
    private @Getter @Setter
    FieldParams context;
    @JsonProperty("nyZmZayN")
    private @Getter @Setter
    FieldParams file;
}
