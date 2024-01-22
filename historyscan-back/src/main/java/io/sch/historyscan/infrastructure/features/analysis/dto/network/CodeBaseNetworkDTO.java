package io.sch.historyscan.infrastructure.features.analysis.dto.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class CodeBaseNetworkDTO extends RepresentationModel<CodeBaseNetworkDTO> {
    private final NetworkNodesDTO network;
    private final List<FileInfoDTO> ignoredRevisions;
    private final List<String> extensions;

    @JsonCreator
    public CodeBaseNetworkDTO(NetworkNodesDTO network, List<FileInfoDTO> ignoredRevisions, List<String> extensions) {
        this.network = network;
        this.ignoredRevisions = ignoredRevisions;
        this.extensions = extensions;
    }

    public NetworkNodesDTO getNetwork() {
        return network;
    }

    public List<FileInfoDTO> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    public List<String> getExtensions() {
        return extensions;
    }
}
