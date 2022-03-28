package com.stolensugar.web.model.requests;

import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
public class GetUserRequest {
    @Setter @Getter private String userId;
}
