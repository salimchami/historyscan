package io.sch.historyscan.infrastructure.config;

import io.sch.historyscan.domain.contexts.codebase.Clone;
import io.sch.historyscan.domain.contexts.codebase.CodeBaseCloner;
import io.sch.historyscan.domain.contexts.codebase.CodeBaseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainInjections {

    @Bean
    public Clone clone(CodeBaseRepository codeBaseRepository) {
        return new CodeBaseCloner(codeBaseRepository);
    }
}
