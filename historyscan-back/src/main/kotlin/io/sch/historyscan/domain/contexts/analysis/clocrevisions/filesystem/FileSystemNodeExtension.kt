package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

class FileSystemNodeExtension(val root: FileSystemNode) {
    val allExtensions: List<String>
        get() {
            val extensions: MutableList<String> = ArrayList()
            collectExtensions(root, extensions)
            return extensions.stream()
                    .distinct()
                    .sorted()
                    .toList()
        }

    private fun collectExtensions(node: FileSystemNode, extensions: MutableList<String>) {
        if (node.isFile) {
            val extension = getExtension(node.name)
            if (extension != null && !extensions.contains(extension)) {
                extensions.add(extension)
            }
        } else {
            for (child in node.children.values) {
                collectExtensions(child, extensions)
            }
        }
    }

    private fun getExtension(fileName: String): String? {
        val index = fileName.lastIndexOf(".")
        if (index != -1 && index < fileName.length - 1) {
            return fileName.substring(index + 1)
        }
        return null
    }
}
