package com.stolensugar.web.model.requests;
import com.stolensugar.web.dynamodb.models.SpokenFormUserModel;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class CreateSpokenFormUserRequest {
    @Getter @Setter private List<SpokenFormUserModel> spokenFormUsers;
}
