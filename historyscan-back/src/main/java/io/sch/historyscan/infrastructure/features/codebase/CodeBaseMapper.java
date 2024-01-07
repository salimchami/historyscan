package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.clone.ClonedCodeBase;
import io.sch.historyscan.domain.contexts.codebase.find.CurrentCodeBase;
import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.info.CodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebasesDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeBaseMapper {
    public AddedCodebaseDTO domainToWeb(ClonedCodeBase codebase) {
        return new AddedCodebaseDTO(codebase.name(), codebase.url(), codebase.currentBranch());
    }

    public CurrentCodebasesDTO domainToWeb(List<CurrentCodeBase> codebases) {
        return new CurrentCodebasesDTO(codebases.stream()
                .map(this::domainToWeb)
                .toList());
    }

    public CurrentCodebaseDTO domainToWeb(CurrentCodeBase codebase) {
        return new CurrentCodebaseDTO(codebase.name(), codebase.url(), codebase.currentBranch());
    }

    public CodebaseDTO codebaseDomainToWeb(CurrentCodeBase codeBase) {
        return new CodebaseDTO(codeBase.name(), codeBase.url(), codeBase.currentBranch());
    }
}
