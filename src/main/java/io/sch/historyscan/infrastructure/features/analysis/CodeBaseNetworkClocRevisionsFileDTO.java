package io.sch.historyscan.infrastructure.features.analysis;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class CodeBaseNetworkClocRevisionsFileDTO extends RepresentationModel<CodeBaseNetworkClocRevisionsFileDTO> {

    private final String name;
    private final long nbRevisions;
    private final List<FileRevisionsLinkDTO> filesLinks;

    @JsonCreator
    public CodeBaseNetworkClocRevisionsFileDTO(String name, long nbRevisions, List<FileRevisionsLinkDTO> filesLinks) {
        this.name = name;
        this.nbRevisions = nbRevisions;
        this.filesLinks = filesLinks;
    }

    public String getName() {
        return name;
    }

    public long getNbRevisions() {
        return nbRevisions;
    }

    public List<FileRevisionsLinkDTO> getFilesLinks() {
        return filesLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CodeBaseNetworkClocRevisionsFileDTO that = (CodeBaseNetworkClocRevisionsFileDTO) o;
        return nbRevisions == that.nbRevisions && Objects.equals(name, that.name) && Objects.equals(filesLinks, that.filesLinks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, nbRevisions, filesLinks);
    }
}
