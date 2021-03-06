package com.stolensugar.web.model.response;
import com.stolensugar.web.model.SpokenFormUser;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class GetSpokenFormByUserResponse {
    @Getter
    @Setter
    private List<SpokenFormUser> users;
}