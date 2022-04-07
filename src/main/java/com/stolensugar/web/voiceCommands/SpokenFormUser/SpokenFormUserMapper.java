package com.stolensugar.web.voiceCommands.SpokenFormUser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SpokenFormUserMapper {
    private @Getter @Setter String repo_id;
    private @Getter @Setter String user_id;
    private @Getter @Setter String timestamp;
    private @Getter @Setter String branch;
    private @Getter @Setter List<Map<String,Object>> command_groups;
}
