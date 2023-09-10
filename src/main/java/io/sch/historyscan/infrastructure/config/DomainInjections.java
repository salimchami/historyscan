package io.sch.historyscan.infrastructure.config;

import io.sch.historyscan.domain.contexts.codebase.clone.Clone;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseCloner;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseRepository;
import io.sch.historyscan.domain.contexts.codebase.find.CodeBasesListInventory;
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBases;
import io.sch.historyscan.domain.contexts.codebase.find.CodeBasesSearch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainInjections {

    @Bean
    public Clone clone(CodeBaseRepository codeBaseRepository) {
        return new CodeBaseCloner(codeBaseRepository);
    }

    @Bean
    public CurrentCodeBases currentCodeBases(CodeBasesListInventory listCodeBaseManagement) {
        return new CodeBasesSearch(listCodeBaseManagement);
    }
}
