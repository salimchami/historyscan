package io.sch.historyscan.domain.contexts.codebase.find;

import java.util.List;

public record CodeBasesSearch(CodeBasesListInventory codeBasesListInventory) implements CurrentCodeBases {

    @Override
    public List<CurrentCodeBase> fromDisk() {
        return codeBasesListInventory.listAll();
    }
}
