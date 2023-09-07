package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.domain.contexts.codebase.Clone;
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
        var codebase = clone.from(codeBaseToAddDTO.url(), codeBaseToAddDTO.publicKey(), codeBaseToAddDTO.branch());
        return codeBaseMapper.domainToWeb(codebase);

    }
}
