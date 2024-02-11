package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

class FileInfo(
    val name: String,
    val path: String,
    val isFile: Boolean,
    val currentNbLines: Long
) :
    Comparable<FileInfo> {
    fun pathParts(rootFolder: String): List<String> {
        val parts: MutableList<String> = ArrayList()
        val rootIndex = this.path.indexOf(rootFolder)
        if (rootIndex != -1) {
            val beforeRoot = path.substring(0, rootIndex + rootFolder.length)
            if (beforeRoot.isNotEmpty()) {
                parts.add(beforeRoot)
            }

            var remainingPath = path.substring(rootIndex + rootFolder.length)
            if (remainingPath.isNotEmpty()) {
                if (remainingPath.startsWith("/")) {
                    remainingPath = remainingPath.substring(1)
                }
                parts.addAll(listOf(*remainingPath.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()))
            }
        } else {
            parts.addAll(listOf(*path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        }

        return parts.stream()
            .filter { part: String -> part.isNotEmpty() }
            .toList()
    }

    fun pathFrom(pathPart: String): String {
        val index = if (pathPart == name || (pathPart.contains("/") && pathPart.endsWith(name))
        ) path.lastIndexOf(pathPart)
        else path.lastIndexOf("$pathPart/")
        return path.substring(0, if (index == 0) pathPart.length else index + pathPart.length)
    }

    fun isFileFrom(pathPart: String, rootFolder: String): Boolean {
        val parts = pathParts(rootFolder)
        val partIndex = parts.indexOf(pathPart)
        if (partIndex == -1 || partIndex != parts.size - 1) {
            return false
        }
        return !path.endsWith("/") && isFile
    }

    override fun compareTo(other: FileInfo): Int = when (
        val valueComparison = path.compareTo(other.path)) {
        0 -> name compareTo other.name
        else -> valueComparison
    }


    fun parentPathFrom(pathPart: String): String? {
        val index = if (pathPart == name) path.lastIndexOf(pathPart)
        else path.lastIndexOf("$pathPart/")
        if (index == 0) {
            return null
        }
        return path.substring(0, index - 1)
    }
}
