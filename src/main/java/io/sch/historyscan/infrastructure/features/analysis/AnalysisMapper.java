package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitInfo;
import io.sch.historyscan.infrastructure.features.analysis.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
                codeBaseHistoryCommitFile.fileInfo().name(),
                codeBaseHistoryCommitFile.fileInfo().path(),
                codeBaseHistoryCommitFile.currentNbLines(),
                codeBaseHistoryCommitFile.nbAddedLines(),
                codeBaseHistoryCommitFile.nbDeletedLines(),
                codeBaseHistoryCommitFile.nbModifiedLines());
    }

    private CodeBaseHistoryCommitInfoDTO domainToWeb(CodeBaseHistoryCommitInfo info) {
        return new CodeBaseHistoryCommitInfoDTO(info.hash(), info.author(), info.date(), info.shortMessage());
    }

    public CodeBaseClocRevisionsDTO domainToWeb(CodebaseClocRevisions analyzedCodeBaseClocRevisions) {
        List<CodeBaseClocRevisionsFileDTO> revisions = new ArrayList<>();
        var root = analyzedCodeBaseClocRevisions.actualFsTree().getRoot();
        convertNode(root, revisions, null);

        return new CodeBaseClocRevisionsDTO(
                revisions,
                analyzedCodeBaseClocRevisions.ignoredRevisions(),
                analyzedCodeBaseClocRevisions.extensions());
    }

    private void convertNode(FileSystemNode node, List<CodeBaseClocRevisionsFileDTO> revisions, String parentPath) {
        CodeBaseClocRevisionsFileDTO dto = new CodeBaseClocRevisionsFileDTO(node.getName(), node.getPath(), parentPath, node.getScore());
        revisions.add(dto);
        for (FileSystemNode child : node.getChildren().values()) {
            convertNode(child, revisions, node.getPath());
        }
    }

}
