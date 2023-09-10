package io.sch.historyscan.domain.contexts.codebase.find;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

import java.util.Optional;

@HexagonalArchitectureSPI
@FunctionalInterface
public interface CodeBaseInfoInventory {
    Optional<CurrentCodeBase> findBy(String name);
}
