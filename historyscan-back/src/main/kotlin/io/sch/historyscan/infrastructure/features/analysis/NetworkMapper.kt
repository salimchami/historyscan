package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo
import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork
import io.sch.historyscan.domain.contexts.analysis.network.NetworkLink
import io.sch.historyscan.domain.contexts.analysis.network.NetworkNode
import io.sch.historyscan.domain.contexts.analysis.network.NetworkNodes
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.network.CodeBaseNetworkDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.network.NetworkLinkDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.network.NetworkNodeDTO
import io.sch.historyscan.infrastructure.features.analysis.dto.network.NetworkNodesDTO
import org.springframework.stereotype.Component

@Component
class NetworkMapper {
    fun domainToWeb(analyzedCodeBaseNetwork: CodebaseRevisionsNetwork): CodeBaseNetworkDTO {
        return CodeBaseNetworkDTO(domainToWeb(analyzedCodeBaseNetwork.network),
                analyzedCodeBaseNetwork.ignoredRevisions.stream().map { fileInfo: FileInfo -> FileInfoDTO(fileInfo.name, fileInfo.path) }.toList(),
                analyzedCodeBaseNetwork.extensions)
    }

    private fun domainToWeb(network: NetworkNodes): NetworkNodesDTO {
        return NetworkNodesDTO(network.nodes.stream()
                .map { node: NetworkNode ->
                    NetworkNodeDTO(
                            node.name,
                            node.path,
                            node.parentPath,
                            node.currentNbLines,
                            node.score,
                            node.links.stream().map { link: NetworkLink ->
                                NetworkLinkDTO(
                                        link.path(), link.weight()
                                )
                            }.toList()
                    )
                }
                .toList())
    }
}
