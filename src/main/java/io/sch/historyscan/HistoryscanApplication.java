package io.sch.historyscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HistoryscanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryscanApplication.class, args);
    }

}
