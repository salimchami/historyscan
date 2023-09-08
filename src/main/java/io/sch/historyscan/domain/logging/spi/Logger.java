package io.sch.historyscan.domain.logging.spi;

public interface Logger {

    void info(String message);

    void error(Exception ex);

    void error(String message);
}
