package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.Clone;
import io.sch.historyscan.domain.contexts.codebase.CodeBaseToClone;
import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.clone.CodeBaseToAddDTO;
import org.springframework.stereotype.Component;

@Component
public class CodeBaseApplication {

    private final Clone clone;
    private final CodeBaseMapper codeBaseMapper;

    public CodeBaseApplication(Clone clone, CodeBaseMapper codeBaseMapper) {
        this.clone = clone;
        this.codeBaseMapper = codeBaseMapper;
    }

    public AddedCodebaseDTO clone(CodeBaseToAddDTO codeBaseToAddDTO) {
        var codebase = clone.from(new CodeBaseToClone(
                codeBaseToAddDTO.url(), codeBaseToAddDTO.publicKey(),
                codeBaseToAddDTO.name(), codeBaseToAddDTO.branch()));
        return codeBaseMapper.domainToWeb(codebase);

    }
}
