package io.sch.historyscan.domain.hexagonalarchitecture;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * API for Hexagonal Architecture
 * <p>
 *     This annotation is used to mark the API from the Hexagonal Architecture.
 *     The API is the set from classes that are exposed to the outside world.
 *     It's the entry point from the Domain.
 * </p>
 */
@Target(ElementType.TYPE)
public @interface HexagonalArchitectureAPI {
}
