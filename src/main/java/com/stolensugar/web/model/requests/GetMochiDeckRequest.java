package com.stolensugar.web.model.requests;

import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
public class GetMochiDeckRequest {
    private @Getter @Setter String userId;
    private @Getter @Setter String app;
    private @Getter @Setter String file;
}
