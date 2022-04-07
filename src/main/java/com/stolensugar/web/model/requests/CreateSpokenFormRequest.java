package com.stolensugar.web.model.requests;

import com.stolensugar.web.dynamodb.models.SpokenFormModel;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
public class CreateSpokenFormRequest {
    @Getter
    @Setter
    private List<SpokenFormModel> spokenForms;
}
