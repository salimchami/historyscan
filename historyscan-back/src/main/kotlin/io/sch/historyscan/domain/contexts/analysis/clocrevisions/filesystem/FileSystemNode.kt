package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.RevisionScore
import io.sch.historyscan.domain.hexagonalarchitecture.DDDEntity

@DDDEntity
class FileSystemNode(
    val name: String,
    val path: String,
    val parentPath: String?,
    val isFile: Boolean,
    val currentNbLines: Long,
    var revisionScore: RevisionScore?
) {
    val children: MutableMap<String, FileSystemNode> = HashMap()

    fun addChild(name: String, node: FileSystemNode) {
        children[name] = node
    }

    fun findChild(path: String): FileSystemNode {
        return children[path] ?: throw NoSuchElementException("No child with path $path")
    }

    fun updateScoreFrom(numberOfRevisions: Int) {
        this.revisionScore = RevisionScore.of(numberOfRevisions, currentNbLines)
    }

    fun updateScoreFrom(score: RevisionScore?) {
        this.revisionScore = score
    }

    fun findFileNode(path: String): FileSystemNode? {
        return findFileNode(this, path)
    }

    private fun findFileNode(current: FileSystemNode, path: String): FileSystemNode? {
        if (current.isFile && (current.path.contains(path))) {
            return current
        } else {
            for (child in current.children.values) {
                val result = findFileNode(child, path)
                if (result != null) {
                    return result
                }
            }
            return null
        }
    }

    val isRoot: Boolean
        get() = name == "root"

    fun updateFolderScore() {
        this.updateFolderScore(this)
    }

    private fun updateFolderScore(node: FileSystemNode): Long {
        if (node.isFile) {
            return node.revisionScore?.score ?: 0
        } else {
            var totalScore = 0L
            for (child in node.children.values) {
                totalScore += updateFolderScore(child)
            }
            node.updateScoreFrom(RevisionScore(totalScore))
            return totalScore
        }
    }

    fun allFileNodes(): List<FileSystemNode> {
        val fileNodes: MutableList<FileSystemNode> = ArrayList()
        if (this.isFile) {
            fileNodes.add(this)
        } else {
            for (child in children.values) {
                fileNodes.addAll(child.allFileNodes())
            }
        }
        return fileNodes
    }
}
