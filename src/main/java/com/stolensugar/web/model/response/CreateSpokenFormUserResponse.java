package com.stolensugar.web.model.response;

import com.stolensugar.web.model.SpokenFormUser;
import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
public class CreateSpokenFormUserResponse {
    @Getter @Setter private SpokenFormUser spokenFormUser;
}
