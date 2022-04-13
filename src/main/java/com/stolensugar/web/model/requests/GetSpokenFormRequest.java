package com.stolensugar.web.model.requests;

import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
public class GetSpokenFormRequest {
    @Setter @Getter private String defaultName;
    @Setter @Getter private String file;
}
