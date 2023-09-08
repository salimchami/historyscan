package io.sch.historyscan.domain.contexts.codebase;

public interface CodeBaseRepository {
    ClonedCodeBase clone(CodeBaseToClone codeBaseToClone);
}
