package io.sch.historyscan.domain.contexts.analysis.network;

import java.util.List;

public record NetworkNode(
        String name,
        String path,
        String parentPath,
        long currentNbLines,
        long score,
        List<NetworkLink> links

) {
}
