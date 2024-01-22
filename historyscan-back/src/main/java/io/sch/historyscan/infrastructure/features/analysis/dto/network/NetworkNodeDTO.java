package io.sch.historyscan.infrastructure.features.analysis.dto.network;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;
import java.util.Objects;

public class NetworkNodeDTO {
    private final String name;
    private final String path;
    private final String parentPath;
    private final long currentNbLines;
    private final long score;
    private final List<NetworkLinkDTO> links;

    @JsonCreator
    public NetworkNodeDTO(String name, String path, String parentPath, long currentNbLines, long score, List<NetworkLinkDTO> links) {
        this.name = name;
        this.path = path;
        this.parentPath = parentPath;
        this.currentNbLines = currentNbLines;
        this.score = score;
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getParentPath() {
        return parentPath;
    }

    public long getCurrentNbLines() {
        return currentNbLines;
    }

    public long getScore() {
        return score;
    }

    public List<NetworkLinkDTO> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkNodeDTO that = (NetworkNodeDTO) o;
        return currentNbLines == that.currentNbLines
               && score == that.score
               && Objects.equals(name, that.name)
               && Objects.equals(path, that.path)
               && Objects.equals(parentPath, that.parentPath)
               && Objects.equals(links, that.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, parentPath, currentNbLines, score, links);
    }
}
