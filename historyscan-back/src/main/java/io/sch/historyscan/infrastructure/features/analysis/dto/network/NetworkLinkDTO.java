package io.sch.historyscan.infrastructure.features.analysis.dto.network;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class NetworkLinkDTO {
    private final String path;
    private final long weight;

    @JsonCreator
    public NetworkLinkDTO(String path, long weight) {
        this.path = path;
        this.weight = weight;
    }

    public String getPath() {
        return path;
    }

    public long getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkLinkDTO that = (NetworkLinkDTO) o;
        return weight == that.weight && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, weight);
    }
}
