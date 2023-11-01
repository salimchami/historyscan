package io.sch.historyscan.domain.hexagonalarchitecture;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * API for Hexagonal Architecture
 * <p>
 *     This annotation is used to mark the API of the Hexagonal Architecture.
 *     The API is the set of classes that are exposed to the outside world.
 *     It's the entry point of the Domain.
 * </p>
 */
@Target(ElementType.TYPE)
public @interface HexagonalArchitectureAPI {
}
