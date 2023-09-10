package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

@HexagonalArchitectureSPI
@FunctionalInterface
public interface CodeBaseRepository {
    ClonedCodeBase clone(CodeBaseToClone codeBaseToClone);
}
