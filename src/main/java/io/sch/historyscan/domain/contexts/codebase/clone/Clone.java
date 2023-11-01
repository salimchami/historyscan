package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI;
import io.sch.historyscan.infrastructure.features.codebase.errors.CodeBaseAlreadyExistsException;

@HexagonalArchitectureAPI
@FunctionalInterface
public interface Clone {
    ClonedCodeBase from(CodeBaseToClone codeBaseToClone) throws CodeBaseAlreadyExistsException;
}
