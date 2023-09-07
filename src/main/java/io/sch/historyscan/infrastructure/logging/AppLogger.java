package io.sch.historyscan.infrastructure.logging;

import io.sch.historyscan.domain.logging.spi.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AppLogger implements Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AppLogger.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }
}
