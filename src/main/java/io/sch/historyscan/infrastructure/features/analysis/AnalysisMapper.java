package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.CodeBaseFile;
import io.sch.historyscan.domain.contexts.analysis.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.CodeBaseHistoryCommitInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnalysisMapper {
    public AnalyzedCodeBaseDTO domainToWeb(CodeBaseHistory analyzedCodeBase) {
        return new AnalyzedCodeBaseDTO(
                analyzedCodeBase.files().stream().map(this::domainToWeb).toList()
        );
    }

    public CodeBaseFileDTO domainToWeb(CodeBaseFile codeBaseFile) {
        return new CodeBaseFileDTO(
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
                codeBaseHistoryCommitFile.nbLinesAdded(),
                codeBaseHistoryCommitFile.nbLinesDeleted()
        );
    }

    private CodeBaseHistoryCommitInfoDTO domainToWeb(CodeBaseHistoryCommitInfo info) {
        return new CodeBaseHistoryCommitInfoDTO(info.hash(), info.author(), info.date(), info.shortMessage());
    }
}
