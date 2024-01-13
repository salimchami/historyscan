package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI;

@HexagonalArchitectureAPI
@FunctionalInterface
public interface Clone {
    ClonedCodeBase from(CodeBaseToClone codeBaseToClone) throws CodeBaseAlreadyExistsException;
}
