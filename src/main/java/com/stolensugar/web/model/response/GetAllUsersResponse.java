package com.stolensugar.web.model.response;
import com.stolensugar.web.model.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class GetAllUsersResponse {
    @Getter
    @Setter
    private List<User> users;
}