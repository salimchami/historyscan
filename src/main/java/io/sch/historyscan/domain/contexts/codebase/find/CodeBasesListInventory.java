package io.sch.historyscan.domain.contexts.codebase.find;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

import java.util.List;

@HexagonalArchitectureSPI
@FunctionalInterface
public interface CodeBasesListInventory {
    List<CurrentCodeBase> listAll();
}
