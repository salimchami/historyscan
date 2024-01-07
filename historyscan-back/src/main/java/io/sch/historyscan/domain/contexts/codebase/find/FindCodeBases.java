package io.sch.historyscan.domain.contexts.codebase.find;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI;

import java.util.List;

@HexagonalArchitectureAPI
@FunctionalInterface
public interface FindCodeBases {
    List<CurrentCodeBase> fromDisk();
}
