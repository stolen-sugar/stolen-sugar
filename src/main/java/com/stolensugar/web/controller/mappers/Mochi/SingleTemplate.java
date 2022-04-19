package com.stolensugar.web.controller.mappers.Mochi;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "~:id",
        "~:name",
        "~:content",
        "~:pos",
        "~:fields"
})

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleTemplate {
    @JsonProperty("~:id")
    private @Getter @Setter String id = "~:ftOIZq7E";
    @JsonProperty("~:name")
    private @Getter @Setter String name = "stolen-sugar";
    @JsonProperty("~:content")
    private @Getter @Setter String content = "**What is talon the phrase for action below?** \n\n> <span style=\"color:red\"><<action>></span>\n\n**context**: <<context>>\n**file**: <<file>>\n---\n**<<phrase>>**";
    @JsonProperty("~:pos")
    private @Getter @Setter String pos = "zU";
    @JsonProperty("~:fields")
    private @Getter @Setter TemplateFields fields;
}
