package io.sch.historyscan.domain.contexts.analysis.network;

import java.util.Comparator;

import static java.util.Collections.reverseOrder;

public record NetworkLink(
        String path,
        long weight
) implements Comparable<NetworkLink> {
    @Override
    public int compareTo(NetworkLink o) {
        return Comparator
                .comparing(NetworkLink::weight, reverseOrder())
                .thenComparing(NetworkLink::path)
                .compare(this, o);
    }
}
