package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

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
        return new CodeBaseNetworkClocRevisionsDTO(toRevisionsMap(revisions.revisions()),
                revisions.ignoredRevisions().stream().map(this::domainToWeb).toList(),
                revisions.extensions());
    }

    private Map<CodeBaseClocRevisionsFileDTO, Map<FileNameDTO, WeightDTO>> toRevisionsMap(Map<CodebaseFileClocRevisions, Map<FileName, Weight>> revisions) {
        return revisions.entrySet().stream().collect(
                toMap(
                        e -> domainToWeb(e.getKey()),
                        e -> e.getValue().entrySet().stream().collect(
                                toMap(
                                        e2 -> new FileNameDTO(e2.getKey().value()),
                                        e2 -> new WeightDTO(e2.getValue().value())
                                )
                        )
                )
        );
    }

    private CodeBaseClocRevisionsFileDTO domainToWeb(CodebaseFileClocRevisions codebaseFileClocRevisions) {
        return new CodeBaseClocRevisionsFileDTO(codebaseFileClocRevisions.fileName(), codebaseFileClocRevisions.numberOfModifs());
    }
}
