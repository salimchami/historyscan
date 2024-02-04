package io.sch.historyscan.domain.contexts.analysis.network;

import java.util.Comparator;
import java.util.List;

public record NetworkNode(
        String name,
        String path,
        String parentPath,
        long currentNbLines,
        long score,
        List<NetworkLink> links
) implements Comparable<NetworkNode> {

    @Override
    public int compareTo(NetworkNode o) {
        return Comparator
                .comparing(NetworkNode::score, Comparator.reverseOrder())
                .thenComparing(NetworkNode::name)
                .compare(this, o);
    }
}
