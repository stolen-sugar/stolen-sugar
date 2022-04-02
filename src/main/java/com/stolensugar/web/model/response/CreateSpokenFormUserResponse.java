package com.stolensugar.web.model.response;

import com.stolensugar.web.model.SpokenFormUser;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class CreateSpokenFormUserResponse {
    @Getter @Setter private List<SpokenFormUser> spokenFormUsers;
}
