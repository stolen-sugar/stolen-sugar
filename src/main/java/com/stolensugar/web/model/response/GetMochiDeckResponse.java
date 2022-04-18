package com.stolensugar.web.model.response;

import lombok.*;
import net.minidev.json.JSONObject;

@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class GetMochiDeckResponse {
    private @Getter @Setter String mochiJson;

}
