package io.sch.historyscan.domain.contexts.analysis.network;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode;
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit;
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory;
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity;

import java.util.ArrayList;
import java.util.List;

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

    public CodebaseRevisionsNetwork calculateNetworkFromHistoryAndRevisions(CodeBaseToAnalyze codeBase) {
        var actualCodebaseFiles = actualFsTree.files();
        var historyCommits = filterHistoryFromActualFs(actualCodebaseFiles, codeBase.getName());
        network = new NetworkNodes(actualCodebaseFiles.stream()
                .map(fsNode ->
                        new NetworkNode(
                                fsNode.getName(), fsNode.getPath(),
                                fsNode.getParentPath(), fsNode.getCurrentNbLines(),
                                fsNode.getScore(), linksFrom(historyCommits, fsNode)))
                .sorted()
                .toList());
        return this;
    }

    /**
     * Removes from history, unexisting files in the file system
     */
    private List<CodeBaseCommit> filterHistoryFromActualFs(List<FileSystemNode> actualCodebaseFiles, String codebaseName) {
        return history.commits().stream()
                .map(commit -> new CodeBaseCommit(commit.info(), commit.files().stream()
                        .filter(commitFile -> actualCodebaseFiles.stream()
                                .anyMatch(actualFile -> actualFile.getPath().contains("%s/%s".formatted(codebaseName, commitFile.path()))))
                        .map(commitFile -> new CodeBaseHistoryCommitFile(
                                new io.sch.historyscan.domain.contexts.analysis.history.FileInfo(
                                        commitFile.fileInfo().name(), String.format("%s/%s", codebaseName, commitFile.path()),
                                        commitFile.fileInfo().isFile()
                                ), commitFile.currentNbLines(), commitFile.nbAddedLines(), commitFile.nbDeletedLines(), commitFile.nbModifiedLines()))
                        .toList()))
                .filter(commit -> !commit.files().isEmpty())
                .toList();
    }

    private List<NetworkLink> linksFrom(List<CodeBaseCommit> history, FileSystemNode fsNode) {
        List<NetworkLink> fsNodeLinks = new ArrayList<>();
        history.stream()
                .filter(commit -> commit.files().stream()
                        .anyMatch(commitFile -> fsNode.getPath().contains(commitFile.path())))
                .forEach(commit -> commit.files()
                        .stream()
                        .filter(commitFile -> !fsNode.getPath().contains(commitFile.path()))
                        .forEach(commitFile -> addOrUpdateLink(commitFile, fsNodeLinks))
                );
        return fsNodeLinks.stream().sorted().toList();
    }

    private static void addOrUpdateLink(CodeBaseHistoryCommitFile commitFile, List<NetworkLink> fsNodeLinks) {
        if (fsNodeLinks.stream().anyMatch(link -> link.path().equals(commitFile.path()))) {
            fsNodeLinks.stream().filter(link -> link.path().equals(commitFile.path()))
                    .findFirst().ifPresent(NetworkLink::incrementWeight);
        } else {
            fsNodeLinks.add(new NetworkLink(commitFile.path(), 1L));
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
