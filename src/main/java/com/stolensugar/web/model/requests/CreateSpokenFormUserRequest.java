package com.stolensugar.web.model.requests;
import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
public class CreateSpokenFormUserRequest {
    @Getter @Setter private String userId;
    @Getter @Setter private String spokenFormId;
    @Getter @Setter private String choice;
}
