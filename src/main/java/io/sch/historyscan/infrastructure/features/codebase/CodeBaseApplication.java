package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.clone.Clone;
import io.sch.historyscan.domain.contexts.codebase.clone.CodeBaseToClone;
import io.sch.historyscan.domain.contexts.codebase.find.FindCodeBase;
import io.sch.historyscan.domain.contexts.codebase.find.FindCodeBases;
import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.clone.CodeBaseToAddDTO;
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebasesDTO;
import io.sch.historyscan.infrastructure.hexagonalarchitecture.HexagonalArchitectureApplication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@HexagonalArchitectureApplication
public class CodeBaseApplication {

    private final Clone clone;
    private final FindCodeBases findCodeBases;
    private final FindCodeBase findCodeBase;
    private final CodeBaseMapper codeBaseMapper;

    public CodeBaseApplication(Clone clone,
                               FindCodeBases findCodeBases,
                               FindCodeBase findCodeBase, CodeBaseMapper codeBaseMapper) {
        this.clone = clone;
        this.findCodeBases = findCodeBases;
        this.findCodeBase = findCodeBase;
        this.codeBaseMapper = codeBaseMapper;
    }

    public AddedCodebaseDTO clone(CodeBaseToAddDTO codeBaseToAddDTO) {
        var codebase = clone.from(new CodeBaseToClone(
                codeBaseToAddDTO.url(), codeBaseToAddDTO.publicKey(),
                codeBaseToAddDTO.name(), codeBaseToAddDTO.branch()));
        return codeBaseMapper.domainToWeb(codebase);

    }

    public Optional<CurrentCodebaseDTO> findCodeBase(String codebaseName) {
        return findCodeBase.from(codebaseName)
                .map(codeBaseMapper::domainToWeb);
    }

    public CurrentCodebasesDTO currentCodeBases() {
        var codebases = findCodeBases.fromDisk();
        return codeBaseMapper.domainToWeb(codebases);
    }
}
