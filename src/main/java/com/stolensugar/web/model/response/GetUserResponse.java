package com.stolensugar.web.model.response;

import lombok.*;
import com.stolensugar.web.model.User;

@EqualsAndHashCode
@ToString
@Builder
public class GetUserResponse {
    @Getter @Setter private User user;
}
