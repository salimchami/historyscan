package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.network.CodebaseRevisionsNetwork;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseNetworkDTO;
import org.springframework.stereotype.Component;

@Component
public class NetworkMapper {

    public CodeBaseNetworkDTO domainToWeb(CodebaseRevisionsNetwork analyzedCodeBaseNetwork) {
        return null;
    }
}
