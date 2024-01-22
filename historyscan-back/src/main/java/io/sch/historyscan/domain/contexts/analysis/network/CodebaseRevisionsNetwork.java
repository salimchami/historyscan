package io.sch.historyscan.domain.contexts.analysis.network;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

import java.util.*;

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
        network = new NetworkNodes(actualFsTree.files().stream()
                .map(fsNode ->
                        new NetworkNode(
                                fsNode.getName(), fsNode.getPath(),
                                fsNode.getParentPath(), fsNode.getCurrentNbLines(),
                                fsNode.getScore(), linksFrom(history.commits(), fsNode)))
                .toList());
        return this;
    }

    private List<NetworkLink> linksFrom(List<CodeBaseCommit> history, FileSystemNode fsNode) {
        Map<String, List<NetworkLink>> linksByFile = new HashMap<>();
        history.stream()
                .filter(commit -> commit.files().stream().anyMatch(file -> file.path().equals(fsNode.getPath())))
                .flatMap(commit -> commit.files().stream())
                .filter(commitFile -> !commitFile.path().equals(fsNode.getPath()))
                .forEach(commitFile -> {
                    linksByFile.computeIfAbsent(commitFile.path(), k -> new ArrayList<>());
                    linksByFile.computeIfPresent(commitFile.path(), (path, fileLinks) -> fileLinksFromExistingList(commitFile, fileLinks));
                });
        return linksByFile.values().stream().flatMap(List::stream).sorted().toList();
    }

    private static List<NetworkLink> fileLinksFromExistingList(CodeBaseHistoryCommitFile commitFile, List<NetworkLink> fileLinks) {
        if (fileLinks.stream().anyMatch(link1 -> link1.path().equals(commitFile.path()))) {
            return fileLinks.stream()
                    .map(link -> link.path().equals(commitFile.path()) ? new NetworkLink(link.path(), link.weight() + 1) : link)
                    .toList();
        } else {
            List<NetworkLink> updatedLinks = new ArrayList<>(fileLinks);
            updatedLinks.add(new NetworkLink(commitFile.path(), 1));
            return updatedLinks;
        }
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
