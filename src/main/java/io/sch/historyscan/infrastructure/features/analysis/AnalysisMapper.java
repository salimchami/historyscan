package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AnalysisMapper {
    public CodeBaseHistoryDTO domainToWeb(CodeBaseHistory analyzedCodeBase) {
        return new CodeBaseHistoryDTO(
                analyzedCodeBase.commits().stream().map(this::domainToWeb).toList()
        );
    }

    public CodeBaseHistoryFileDTO domainToWeb(CodeBaseCommit codeBaseCommit) {
        return new CodeBaseHistoryFileDTO(
                this.domainToWeb(codeBaseCommit.info()),
                this.domainToWeb(codeBaseCommit.files())
        );
    }

    private List<CodeBaseHistoryCommitFileDTO> domainToWeb(List<CodeBaseHistoryCommitFile> files) {
        return files.stream().map(this::domainToWeb).toList();
    }

    private CodeBaseHistoryCommitFileDTO domainToWeb(CodeBaseHistoryCommitFile codeBaseHistoryCommitFile) {
        return new CodeBaseHistoryCommitFileDTO(
                codeBaseHistoryCommitFile.fileName(),
                codeBaseHistoryCommitFile.nbAddedLines(),
                codeBaseHistoryCommitFile.nbDeletedLines(),
                codeBaseHistoryCommitFile.nbModifiedLines());
    }

    private CodeBaseHistoryCommitInfoDTO domainToWeb(CodeBaseHistoryCommitInfo info) {
        return new CodeBaseHistoryCommitInfoDTO(info.hash(), info.author(), info.date(), info.shortMessage());
    }

    public CodeBaseClocRevisionsDTO domainToWeb(CodebaseClocRevisions analyzedCodeBaseClocRevisions) {
        return new CodeBaseClocRevisionsDTO(
                analyzedCodeBaseClocRevisions.revisions().stream().map(this::domainToWeb).toList(),
                analyzedCodeBaseClocRevisions.ignoredRevisions().stream().map(this::domainToWeb).toList(),
                analyzedCodeBaseClocRevisions.extensions());
    }

    public CodeBaseNetworkClocRevisionsDTO domainToWeb(CodebaseNetworkClocRevisions revisions) {
        return new CodeBaseNetworkClocRevisionsDTO(toRevisionList(revisions.revisions()),
                revisions.ignoredRevisions().stream().map(this::domainToWeb).toList(),
                revisions.extensions());
    }

    private List<CodeBaseNetworkClocRevisionsFileDTO> toRevisionList(Map<CodebaseFileClocRevisions, Map<FileName, Weight>> revisions) {
        return revisions.entrySet().stream()
                .map(entry -> new CodeBaseNetworkClocRevisionsFileDTO(
                        entry.getKey().fileName(),
                        entry.getKey().numberOfModifs(),
                        entry.getValue().entrySet().stream()
                                .map(e ->
                                        new FileRevisionsLinkDTO(
                                                e.getKey().value(),
                                                e.getValue().value()))
                                .toList()
                ))
                .toList();
    }

    private CodeBaseClocRevisionsFileDTO domainToWeb(CodebaseFileClocRevisions codebaseFileClocRevisions) {
        return new CodeBaseClocRevisionsFileDTO(codebaseFileClocRevisions.fileName(), codebaseFileClocRevisions.numberOfModifs());
    }
}
