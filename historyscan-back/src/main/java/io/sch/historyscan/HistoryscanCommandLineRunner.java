package io.sch.historyscan;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("interactive")
public class HistoryscanCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
