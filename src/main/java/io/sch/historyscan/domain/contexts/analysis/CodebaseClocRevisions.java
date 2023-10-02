package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;

public record CodebaseClocRevisions(List<CodebaseFileClocRevisions> revisions) {
}
