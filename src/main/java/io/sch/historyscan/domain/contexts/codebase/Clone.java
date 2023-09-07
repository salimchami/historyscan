package io.sch.historyscan.domain.contexts.codebase;

public interface Clone {
    ClonedCodeBase from(String url, String publicKey, String branch);
}
