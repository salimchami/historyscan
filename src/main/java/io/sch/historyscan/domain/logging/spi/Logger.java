package io.sch.historyscan.domain.logging.spi;

import io.sch.historyscan.domain.hexagonalarchitecture.HexagonalArchitectureSPI;

@HexagonalArchitectureSPI
public interface Logger {

    void info(String message);

    void error(Exception ex);

    void error(String message);

    void debug(String message);

    void error(String message, Exception exception);
}
