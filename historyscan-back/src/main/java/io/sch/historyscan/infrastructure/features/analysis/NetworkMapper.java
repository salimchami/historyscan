package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork;
import io.sch.historyscan.domain.contexts.analysis.network.NetworkNodes;
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.network.CodeBaseNetworkDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.network.NetworkLinkDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.network.NetworkNodeDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.network.NetworkNodesDTO;
import org.springframework.stereotype.Component;

@Component
public class NetworkMapper {

    public CodeBaseNetworkDTO domainToWeb(CodebaseRevisionsNetwork analyzedCodeBaseNetwork) {
        return new CodeBaseNetworkDTO(domainToWeb(analyzedCodeBaseNetwork.getNetwork()),
                analyzedCodeBaseNetwork.getIgnoredRevisions().stream().map(
                        fileInfo -> new FileInfoDTO(fileInfo.name(), fileInfo.path())
                ).toList(),
                analyzedCodeBaseNetwork.getExtensions());
    }

    private NetworkNodesDTO domainToWeb(NetworkNodes network) {
        return new NetworkNodesDTO(network.nodes().stream()
                .map(node -> new NetworkNodeDTO(
                        node.name(),
                        node.path(),
                        node.parentPath(),
                        node.currentNbLines(),
                        node.score(),
                        node.links().stream().map(link -> new NetworkLinkDTO(
                                link.path(), link.weight()
                        )).toList()
                ))
                .toList());
    }
}
