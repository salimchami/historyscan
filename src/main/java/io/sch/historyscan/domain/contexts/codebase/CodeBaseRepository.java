package io.sch.historyscan.domain.contexts.codebase;

public interface CodeBaseRepository {
    ClonedCodeBase clone(String url, String publicKey, String branch);
}
