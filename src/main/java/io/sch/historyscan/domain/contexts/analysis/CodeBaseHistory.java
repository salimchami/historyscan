package io.sch.historyscan.domain.contexts.analysis;

import java.util.List;

public record CodeBaseHistory(List<CodeBaseHistoryCommit> commits) {
}
