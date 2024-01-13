package io.sch.historyscan.domain.contexts.codebase.clone;

import io.sch.historyscan.domain.hexagonalarchitecture.DDDService;

@DDDService
public record CodeBaseCloner(CodeBaseRepository codeBaseRepository) implements Clone {

    @Override
    public ClonedCodeBase from(CodeBaseToClone codeBaseToClone) throws CodeBaseAlreadyExistsException {
        return codeBaseRepository.clone(codeBaseToClone);
    }
}
