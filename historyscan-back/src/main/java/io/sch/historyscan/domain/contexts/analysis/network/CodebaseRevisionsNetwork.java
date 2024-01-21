package io.sch.historyscan.domain.contexts.analysis.network;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DDDEntity
public class CodebaseRevisionsNetwork {
    private NetworkNodes network;
    private final FileSystemTree actualFsTree;
    private final CodeBaseHistory history;
    private final List<FileInfo> ignoredRevisions;
    private final List<String> extensions;

    public CodebaseRevisionsNetwork(CodeBaseHistory history, CodebaseClocRevisions clocRevisions) {
        this.history = history;
        this.ignoredRevisions = clocRevisions.ignoredRevisions();
        this.extensions = clocRevisions.extensions();
        this.actualFsTree = clocRevisions.actualFsTree();
    }

    public CodebaseRevisionsNetwork calculateNetworkFromHistoryAndRevisions() {
        network = new NetworkNodes(actualFsTree.files().stream().map(fsNode -> new NetworkNode(fsNode.getName(), fsNode.getPath(), fsNode.getParentPath(), fsNode.getCurrentNbLines(), fsNode.getScore(), linksFrom(history.commits(), fsNode))).toList());
        return this;
    }

    private List<NetworkLink> linksFrom(List<CodeBaseCommit> history, FileSystemNode fsNode) {
        Map<String, List<NetworkLink>> links = new HashMap<>();
        history.stream()
                .filter(commit -> commit.files().stream().anyMatch(file -> file.path().equals(fsNode.getPath())))
                .forEach(commit -> commit.files().stream()
                        .filter(file -> !file.path().equals(fsNode.getPath()))
                        .forEach(file -> {
                            List<NetworkLink> fileLinks = links.getOrDefault(file.path(), new ArrayList<>());
                            fileLinks.add(new NetworkLink(file.path(), 1));
                            links.put(file.path(), fileLinks);
                        }));
        return links.values().stream().flatMap(List::stream).toList();
    }

    public NetworkNodes getNetwork() {
        return network;
    }

    public List<FileInfo> getIgnoredRevisions() {
        return ignoredRevisions;
    }

    public List<String> getExtensions() {
        return extensions;
    }
}
