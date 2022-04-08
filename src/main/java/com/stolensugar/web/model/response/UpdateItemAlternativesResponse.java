package com.stolensugar.web.model.response;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class UpdateItemAlternativesResponse {
    @Setter @Getter private List<String> messages;
}
