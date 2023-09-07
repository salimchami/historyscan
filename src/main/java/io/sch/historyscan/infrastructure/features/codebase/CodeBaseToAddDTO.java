package io.sch.historyscan.infrastructure.features.codebase;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record CodeBaseToAddDTO(@JsonProperty("url") String url,
                               @JsonProperty("public-key") String publicKey,
                               @JsonProperty("branch") String branch
) {
}
