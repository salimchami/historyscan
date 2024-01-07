package io.sch.historyscan.domain.contexts.analysis.history;

import java.time.LocalDateTime;

public record CodeBaseHistoryCommitInfo(String hash, String author, LocalDateTime date, String shortMessage) {
}
