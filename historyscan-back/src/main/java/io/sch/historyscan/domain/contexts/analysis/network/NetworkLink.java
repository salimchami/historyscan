package io.sch.historyscan.domain.contexts.analysis.network;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Collections.reverseOrder;

public class NetworkLink implements Comparable<NetworkLink> {
    private final String path;
    private long weight;

    public NetworkLink(String path, long weight) {
        this.path = path;
        this.weight = weight;
    }

    public void incrementWeight() {
        this.weight = weight + 1;
    }

    public String path() {
        return path;
    }

    public long weight() {
        return weight;
    }

    @Override
    public int compareTo(NetworkLink o) {
        return Comparator
                .comparing(NetworkLink::weight, reverseOrder())
                .thenComparing(NetworkLink::path)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (NetworkLink) o;
        return Objects.equals(path, that.path) && weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, weight);
    }
}
