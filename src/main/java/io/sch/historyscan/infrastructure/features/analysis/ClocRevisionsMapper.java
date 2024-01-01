package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseClocRevisionsDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.CodeBaseClocRevisionsFileDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClocRevisionsMapper {
    public CodeBaseClocRevisionsDTO domainToWeb(CodebaseClocRevisions analyzedCodeBaseClocRevisions) {
        List<CodeBaseClocRevisionsFileDTO> revisions = new ArrayList<>();
        var root = analyzedCodeBaseClocRevisions.actualFsTree().getRoot();
        convertNode(root, revisions);

        return new CodeBaseClocRevisionsDTO(
                revisions,
                analyzedCodeBaseClocRevisions.ignoredRevisions(),
                analyzedCodeBaseClocRevisions.extensions());
    }

    private void convertNode(FileSystemNode node, List<CodeBaseClocRevisionsFileDTO> revisions) {
        CodeBaseClocRevisionsFileDTO dto = new CodeBaseClocRevisionsFileDTO(node.getName(), node.getPath(), node.getParentPath(), node.getScore());
        revisions.add(dto);
        for (FileSystemNode child : node.getChildren().values()) {
            convertNode(child, revisions);
        }
    }

}
