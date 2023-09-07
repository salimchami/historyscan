package io.sch.historyscan.domain.contexts.codebase;

public record CodeBaseCloner(CodeBaseRepository codeBaseRepository) implements Clone {

    @Override
    public ClonedCodeBase from(String url, String publicKey, String branch) {
        return codeBaseRepository.clone(url, publicKey, branch);
    }
}
