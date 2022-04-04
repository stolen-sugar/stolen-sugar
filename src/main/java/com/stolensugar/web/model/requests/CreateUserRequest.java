package com.stolensugar.web.model.requests;

import com.stolensugar.web.dynamodb.models.UserModel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class CreateUserRequest {
    @Getter
    @Setter
    private List<UserModel> users;
}
