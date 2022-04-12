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
    private @Getter @Setter Name name;
    @JsonProperty("choice123")
    private @Getter @Setter
    Choice123 choice123;
}
