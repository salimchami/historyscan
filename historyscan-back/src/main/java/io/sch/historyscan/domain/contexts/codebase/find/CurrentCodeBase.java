package io.sch.historyscan.domain.contexts.codebase.find;

import io.sch.historyscan.domain.hexagonalarchitecture.DDDValueObject;

@DDDValueObject
public record CurrentCodeBase(String name,
                              String url,
                              String currentBranch,
                              boolean gitError) {
}
