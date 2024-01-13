package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

import java.io.File;

@HexagonalArchitectureSPI
public interface CodeBaseRepository {
    ClonedCodeBase clone(CodeBaseToClone codeBaseToClone) throws CodeBaseAlreadyExistsException;

    ClonedCodeBase pull(File codebase, CodeBaseToClone codeBaseToClone);
}
