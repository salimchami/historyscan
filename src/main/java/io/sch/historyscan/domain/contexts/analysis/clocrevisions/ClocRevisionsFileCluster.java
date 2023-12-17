package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.util.List;

public record ClocRevisionsFileCluster(String folder, List<ClocRevisionsFile> files) {

}
