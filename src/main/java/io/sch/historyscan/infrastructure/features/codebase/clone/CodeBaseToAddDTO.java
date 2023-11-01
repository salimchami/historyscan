package io.sch.historyscan.infrastructure.features.codebase.clone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record CodeBaseToAddDTO(String url,
                               @JsonProperty("public-key") String publicKey,
                               String name,
                               String branch
) {
}
