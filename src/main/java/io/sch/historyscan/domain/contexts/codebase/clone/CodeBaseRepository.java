package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;
import io.sch.historyscan.infrastructure.features.codebase.errors.CodeBaseAlreadyExistsException;

import java.io.File;

@HexagonalArchitectureSPI
public interface CodeBaseRepository {
    ClonedCodeBase clone(CodeBaseToClone codeBaseToClone) throws CodeBaseAlreadyExistsException;

    ClonedCodeBase pull(File codebase, CodeBaseToClone codeBaseToClone);
}
