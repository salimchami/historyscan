package io.sch.historyscan.domain.contexts.analysis.network

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.CodebaseClocRevisions
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemNode
import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileSystemTree
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseCommit
import io.sch.historyscan.domain.contexts.analysis.common.CodeBaseToAnalyze
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistory
import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile
import io.sch.historyscan.domain.contexts.analysis.history.FileInfo
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity

@DDDEntity
class CodebaseRevisionsNetwork(private val history: CodeBaseHistory, clocRevisions: CodebaseClocRevisions) {
    var network: NetworkNodes = NetworkNodes(emptyList())
        private set
    private val actualFsTree: FileSystemTree = clocRevisions.actualFsTree
    val ignoredRevisions: List<io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FileInfo?> = clocRevisions.ignoredRevisions
    val extensions: List<String> = clocRevisions.extensions

    fun calculateNetworkFromHistoryAndRevisions(codeBase: CodeBaseToAnalyze): CodebaseRevisionsNetwork {
        val actualCodebaseFiles = actualFsTree.files()
        val historyCommits = filterHistoryFromActualFs(actualCodebaseFiles, codeBase.name)
        network = NetworkNodes(actualCodebaseFiles.stream()
                .map { fsNode: FileSystemNode ->
                    NetworkNode(
                            fsNode.name, fsNode.path,
                            fsNode.parentPath!!, fsNode.currentNbLines,
                            fsNode.revisionScore?.score!!, linksFrom(historyCommits, fsNode))
                }
                .sorted()
                .toList())
        return this
    }

    /**
     * Removes from history, unexisting files in the file system
     */
    private fun filterHistoryFromActualFs(actualCodebaseFiles: List<FileSystemNode>, codebaseName: String): List<CodeBaseCommit> {
        return history.commits.stream()
                .map { commit: CodeBaseCommit ->
                    CodeBaseCommit(commit.info, commit.files.stream()
                            .filter { commitFile: CodeBaseHistoryCommitFile ->
                                actualCodebaseFiles.stream()
                                        .anyMatch { actualFile: FileSystemNode -> actualFile.path.contains("%s/%s".format(codebaseName, commitFile.path())) }
                            }
                            .map { commitFile: CodeBaseHistoryCommitFile ->
                                CodeBaseHistoryCommitFile(
                                        FileInfo(
                                                commitFile.fileInfo.name, String.format("%s/%s", codebaseName, commitFile.path()),
                                                commitFile.fileInfo.isFile
                                        ), commitFile.currentNbLines, commitFile.nbAddedLines, commitFile.nbDeletedLines, commitFile.nbModifiedLines)
                            }
                            .toList())
                }
                .filter { commit: CodeBaseCommit -> commit.files.isNotEmpty() }
                .toList()
    }

    private fun linksFrom(history: List<CodeBaseCommit>, fsNode: FileSystemNode): List<NetworkLink> {
        val fsNodeLinks: MutableList<NetworkLink> = ArrayList()
        history.stream()
                .filter { commit: CodeBaseCommit ->
                    commit.files.stream()
                            .anyMatch { commitFile: CodeBaseHistoryCommitFile -> fsNode.path.contains(commitFile.path()) }
                }
                .forEach { commit: CodeBaseCommit ->
                    commit.files
                            .stream()
                            .filter { commitFile: CodeBaseHistoryCommitFile -> !fsNode.path.contains(commitFile.path()) }
                            .forEach { commitFile: CodeBaseHistoryCommitFile -> addOrUpdateLink(commitFile, fsNodeLinks) }
                }
        return fsNodeLinks.stream().sorted().toList()
    }

    companion object {
        private fun addOrUpdateLink(commitFile: CodeBaseHistoryCommitFile, fsNodeLinks: MutableList<NetworkLink>) {
            if (fsNodeLinks.stream().anyMatch { link: NetworkLink -> link.path == commitFile.path() }) {
                fsNodeLinks.stream().filter { link: NetworkLink -> link.path == commitFile.path() }
                        .findFirst().ifPresent { obj: NetworkLink -> obj.incrementWeight() }
            } else {
                fsNodeLinks.add(NetworkLink(commitFile.path(), 1L))
            }
        }
    }
}
