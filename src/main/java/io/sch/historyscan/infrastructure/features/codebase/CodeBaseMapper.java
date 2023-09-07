package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.ClonedCodeBase;
import org.springframework.stereotype.Component;

@Component
public class CodeBaseMapper {
    public AddedCodebaseDTO domainToWeb(ClonedCodeBase codebase) {
        return new AddedCodebaseDTO(codebase.name(), codebase.url(), codebase.currentBranch());
    }
}
