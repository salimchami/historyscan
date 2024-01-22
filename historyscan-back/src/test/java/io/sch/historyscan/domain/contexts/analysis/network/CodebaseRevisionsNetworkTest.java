package io.sch.historyscan.domain.contexts.analysis.network;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.sch.historyscan.domain.contexts.analysis.network.NetworkNodesSerializerUtils.serializeExpected;
import static io.sch.historyscan.fake.CodeBaseCommitFake.defaultHistory;
import static io.sch.historyscan.fake.FileSystemTreeFake.create;
import static org.assertj.core.api.Assertions.assertThat;

class CodebaseRevisionsNetworkTest {
    @Test
    void should_create_network_from_history_and_revisions() throws IOException {
        var fsTree = create("/");
        var clocRevisions = new CodebaseClocRevisions(fsTree, List.of(), List.of("java"));
        var sut = new CodebaseRevisionsNetwork(defaultHistory(), clocRevisions);

        var actualNetwork = sut.calculateNetworkFromHistoryAndRevisions();

        assertThat(actualNetwork)
                .extracting(CodebaseRevisionsNetwork::getNetwork)
                .isEqualTo(serializeExpected("domain-codebase-network"));
    }

}