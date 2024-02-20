package io.sch.historyscan.domain.contexts.analysis.network

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.List
import java.util.stream.Stream

internal class NetworkLinkTest {
    @Test
    fun should_sort_links() {
        val link1 = NetworkLink(
            "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureB/FinallyLoading.java",
            15
        )
        val link2 = NetworkLink(
            "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA/refactored/Extensions.java",
            14
        )
        val link3 = NetworkLink(
            "theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureB/refactored/Loading.java",
            14
        )
        val link4 = NetworkLink("b", 15)
        val link5 = NetworkLink("z", 1)
        val networkLinks = Stream.of(link5, link4, link3, link1, link2).sorted().toList()
        Assertions.assertThat(networkLinks).isEqualTo(List.of(link4, link1, link2, link3, link5))
    }
}