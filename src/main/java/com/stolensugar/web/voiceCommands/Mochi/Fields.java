package com.stolensugar.web.voiceCommands.Mochi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "~:name",
        "~:jvjwdOZ1",
        "~:Spe228xW",
        "~:nyZmZayN"
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Fields {
    @JsonProperty("~:name")
    private @Getter @Setter FieldParams name;
    @JsonProperty("~:jvjwdOZ1")
    private @Getter @Setter FieldParams choice;
    @JsonProperty("~:Spe228xW")
    private @Getter @Setter FieldParams context;
    @JsonProperty("~:nyZmZayN")
    private @Getter @Setter FieldParams file;
}