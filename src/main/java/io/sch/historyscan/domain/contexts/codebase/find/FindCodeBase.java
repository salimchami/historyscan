package io.sch.historyscan.domain.contexts.codebase.find;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI;

import java.util.Optional;

@HexagonalArchitectureAPI
@FunctionalInterface
public interface FindCodeBase {
    Optional<CurrentCodeBase> from(String name);
}
