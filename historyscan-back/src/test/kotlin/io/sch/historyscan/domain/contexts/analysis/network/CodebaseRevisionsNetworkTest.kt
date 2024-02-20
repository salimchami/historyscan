package io.sch.historyscan.domain.contexts.analysis.network

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.*
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze.Companion.of
import io.sch.historyscan.domain.contexts.analysis.common.EnumAnalysisType
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork
import io.sch.historyscan.fake.CodeBaseCommitFake
import io.sch.historyscan.fake.FileSystemTreeFake
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

internal class CodebaseRevisionsNetworkTest {
    @Test
    @Throws(IOException::class)
    fun should_create_network_from_history_and_revisions() {
        val fsTree = FileSystemTreeFake.create("/")
        val clocRevisions = CodebaseClocRevisions(fsTree!!, listOf(), listOf("java"))
        val sut = CodebaseRevisionsNetwork(CodeBaseCommitFake.defaultHistory()!!, clocRevisions)
        val codeBaseToAnalyze = of("theglobalproject", EnumAnalysisType.NETWORK.title, "/")
        val actualNetwork = sut.calculateNetworkFromHistoryAndRevisions(codeBaseToAnalyze)

        Assertions.assertThat(actualNetwork)
            .extracting(CodebaseRevisionsNetwork::network)
            .isEqualTo(NetworkNodesSerializerUtils.serializeExpected("domain-codebase-network"))
    }
}