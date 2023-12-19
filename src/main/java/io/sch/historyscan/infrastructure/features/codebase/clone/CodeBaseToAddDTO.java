package io.sch.historyscan.infrastructure.features.codebase.clone;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record CodeBaseToAddDTO(String url,
                               String publicKey,
                               String name,
                               String branch
) {
}
