package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    private List<FileRevisionsLinkDTO> toRevisionList(Map<CodebaseFileClocRevisions, Map<FileName, Weight>> revisions) {
        return revisions.entrySet().stream()
                .flatMap(baseEntry -> {
                    final String filename = Paths.get(baseEntry.getKey().fileName()).getFileName().toString();
                    if (baseEntry.getValue().isEmpty()) {
                        return Stream.of(new FileRevisionsLinkDTO(filename, baseEntry.getKey().numberOfModifs(), null, null));
                    }
                    return baseEntry.getValue().entrySet().stream()
                            .map(entry -> new FileRevisionsLinkDTO(filename, baseEntry.getKey().numberOfModifs(), entry.getKey().value(), entry.getValue().value()));
                }).toList();
    }

    private CodeBaseClocRevisionsFileDTO domainToWeb(CodebaseFileClocRevisions codebaseFileClocRevisions) {
        return new CodeBaseClocRevisionsFileDTO(codebaseFileClocRevisions.fileName(), codebaseFileClocRevisions.numberOfModifs());
    }
}
