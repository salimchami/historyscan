package io.sch.historyscan.domain.hexagonalarchitecture

/**
 * This annotation is used to mark a class as an SPI (Service Provider Interface) from the hexagonal architecture.
 *
 *
 * The hexagonal architecture is a way to structure your codebase in a way that the business logic is decoupled
 * from the technical details. This is done by defining a core domain (the business logic) and a set from ports
 * (interfaces) that the core domain can use to communicate with the outside world. The outside world can then
 * implement these ports (adapters) to communicate with the core domain.
 *
 */
@Target(AnnotationTarget.CLASS)
annotation class HexagonalArchitectureSPI
