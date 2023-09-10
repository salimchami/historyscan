package io.sch.historyscan.domain.contexts.codebase.find;

import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

@DDDEntity
public record CurrentCodeBase(String name,
                              String url,
                              String currentBranch,
                              boolean error) {
}
