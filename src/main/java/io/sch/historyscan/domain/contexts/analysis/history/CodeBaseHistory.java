package io.sch.historyscan.domain.contexts.analysis.history;

import io.sch.historyscan.domain.contexts.analysis.CodeBaseCommit;

import java.util.List;

public record CodeBaseHistory(List<CodeBaseCommit> commits) {
}
