package io.sch.historyscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableCaching
@PropertySource(value = {"classpath:application.properties", "classpath:secret-application.properties"})
public class HistoryscanApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryscanApplication.class, args);
    }

}
