package com.stolensugar.web.controller.mappers.Mochi;

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
public class TemplateFields {
    @JsonProperty("~:name")
    private @Getter @Setter
    com.stolensugar.web.controller.mappers.Mochi.TemplateFieldParams name;
    @JsonProperty("~:jvjwdOZ1")
    private @Getter @Setter
    com.stolensugar.web.controller.mappers.Mochi.TemplateFieldParams choice;
    @JsonProperty("~:Spe228xW")
    private @Getter @Setter
    com.stolensugar.web.controller.mappers.Mochi.TemplateFieldParams context;
    @JsonProperty("~:nyZmZayN")
    private @Getter @Setter
    com.stolensugar.web.controller.mappers.Mochi.TemplateFieldParams file;
}