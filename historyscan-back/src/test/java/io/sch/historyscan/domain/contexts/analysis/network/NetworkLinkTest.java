package io.sch.historyscan.domain.contexts.analysis.network;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkLinkTest {

    @Test
    void should_sort_links() {
        final NetworkLink link1 = new NetworkLink("theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureB/FinallyLoading.java", 15);
        final NetworkLink link2 = new NetworkLink("theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA/refactored/Extensions.java", 14);
        final NetworkLink link3 = new NetworkLink("theglobalproject/src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureB/refactored/Loading.java", 14);
        final NetworkLink link4 = new NetworkLink("b", 15);
        final NetworkLink link5 = new NetworkLink("z", 1);
        var networkLinks = Stream.of(link5, link4, link3, link1, link2).sorted().toList();
        assertThat(networkLinks).isEqualTo(List.of(link4, link1, link2, link3, link5));
    }
}