package io.sch.historyscan.domain.contexts.codebase;

public record CodeBaseCloner(CodeBaseRepository codeBaseRepository) implements Clone {

    @Override
    public ClonedCodeBase from(CodeBaseToClone codeBaseToClone) {
        return codeBaseRepository.clone(codeBaseToClone);
    }
}
