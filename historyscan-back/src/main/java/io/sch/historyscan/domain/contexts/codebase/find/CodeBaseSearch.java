package io.sch.historyscan.domain.contexts.codebase.find;

import java.util.Optional;

public record CodeBaseSearch(CodeBaseInfoInventory codeBaseInfoInventory) implements FindCodeBase {

    @Override
    public Optional<CurrentCodeBase> from(String name) {
        return codeBaseInfoInventory.findBy(name);
    }
}
