package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalysisMapper {
    public CodeBaseHistoryDTO domainToWeb(CodeBaseHistory analyzedCodeBase) {
        return new CodeBaseHistoryDTO(
                analyzedCodeBase.files().stream().map(this::domainToWeb).toList()
        );
    }

    public CodeBaseHistoryFileDTO domainToWeb(CodeBaseFile codeBaseFile) {
        return new CodeBaseHistoryFileDTO(
                this.domainToWeb(codeBaseFile.info()),
                this.domainToWeb(codeBaseFile.files())
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
                analyzedCodeBaseClocRevisions.ignoredRevisions().stream().map(this::domainToWeb).toList());
    }

    private CodeBaseClocRevisionsFileDTO domainToWeb(CodebaseFileClocRevisions codebaseFileClocRevisions) {
        return new CodeBaseClocRevisionsFileDTO(codebaseFileClocRevisions.fileName(), codebaseFileClocRevisions.numberOfModifs());
    }
}
