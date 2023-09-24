package io.sch.historyscan.domain.contexts.analysis;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureAPI;

@HexagonalArchitectureAPI
@FunctionalInterface
public interface Analysis {

    CodeBaseHistory of(CodeBase codeBase);
}
