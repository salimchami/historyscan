package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.ClonedCodeBase;
import io.sch.historyscan.domain.contexts.codebase.CodeBaseRepository;
import org.springframework.stereotype.Component;

@Component
public record CodeBaseManagementRepository() implements CodeBaseRepository {
    @Override
    public ClonedCodeBase clone(String url, String publicKey, String branch) {

        return null;
    }
}
