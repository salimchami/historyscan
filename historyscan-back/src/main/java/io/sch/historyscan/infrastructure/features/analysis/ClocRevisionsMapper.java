package io.sch.historyscan.infrastructure.features.analysis;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions.ClocRevisionsFileNodeDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.clocrevisions.CodeBaseClocRevisionsDTO;
import io.sch.historyscan.infrastructure.features.analysis.dto.FileInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ClocRevisionsMapper {
    public CodeBaseClocRevisionsDTO domainToWeb(CodebaseClocRevisions analyzedCodeBaseClocRevisions) {
        var root = analyzedCodeBaseClocRevisions.actualFsTree().getRoot();

        return new CodeBaseClocRevisionsDTO(
                domainToWeb(root),
                analyzedCodeBaseClocRevisions.ignoredRevisions().stream().map(this::fileInfoDomainToWeb).toList(),
                analyzedCodeBaseClocRevisions.extensions());
    }

    private ClocRevisionsFileNodeDTO domainToWeb(FileSystemNode node) {
        return new ClocRevisionsFileNodeDTO(node.getName(), node.getPath(), node.getParentPath(), node.isFile(),
                        node.getCurrentNbLines(), node.getScore(), domainToWebChildren(node.getChildren()));
    }

    private List<ClocRevisionsFileNodeDTO> domainToWebChildren(Map<String, FileSystemNode> children) {
        return children.entrySet().stream().map(this::domainToWebEntries).toList();
    }

    private ClocRevisionsFileNodeDTO domainToWebEntries(Map.Entry<String, FileSystemNode> stringFileSystemNodeEntry) {
        return new ClocRevisionsFileNodeDTO(stringFileSystemNodeEntry.getValue().getName(),
                stringFileSystemNodeEntry.getValue().getPath(),
                stringFileSystemNodeEntry.getValue().getParentPath(),
                stringFileSystemNodeEntry.getValue().isFile(),
                stringFileSystemNodeEntry.getValue().getCurrentNbLines(),
                stringFileSystemNodeEntry.getValue().getScore(),
                domainToWebChildren(stringFileSystemNodeEntry.getValue().getChildren()));
    }

    private FileInfoDTO fileInfoDomainToWeb(FileInfo fileInfo) {
        return new FileInfoDTO(fileInfo.name(), fileInfo.path());
    }
}
