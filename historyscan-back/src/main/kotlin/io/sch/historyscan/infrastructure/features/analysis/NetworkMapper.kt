package io.sch.historyscan.infrastructure.features.analysis

import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork
import io.sch.historyscan.domain.contexts.analysis.network.NetworkLink
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
        return CodeBaseNetworkDTO(
            domainToWeb(analyzedCodeBaseNetwork.network),
            analyzedCodeBaseNetwork.ignoredRevisions
                .map { FileInfoDTO(it.name, it.path) }.toList(),
            analyzedCodeBaseNetwork.extensions
        )
    }

    private fun domainToWeb(network: NetworkNodes): NetworkNodesDTO {
        return NetworkNodesDTO(network.nodes
            .map {
                NetworkNodeDTO(
                    it.name,
                    it.path,
                    it.parentPath,
                    it.currentNbLines,
                    it.score,
                    it.links.stream().map { link: NetworkLink ->
                        NetworkLinkDTO(
                            link.path, link.weight
                        )
                    }.toList()
                )
            }
            .toList())
    }
}
