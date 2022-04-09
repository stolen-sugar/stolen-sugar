package com.stolensugar.web.model.response;
import java.util.List;
import java.util.Map;

import com.stolensugar.web.model.SpokenForm;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
public class GetSpokenFormResponse {
    @Getter
    @Setter
    private List<SpokenForm> spokenForms;
    @Getter
    @Setter
    private Map<String, List<SpokenForm>> spokenFormGroups;
}
