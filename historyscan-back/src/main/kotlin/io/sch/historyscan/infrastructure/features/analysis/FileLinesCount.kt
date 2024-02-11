package io.sch.historyscan.infrastructure.features.analysis

import java.nio.charset.StandardCharsets

@JvmRecord
data class FileLinesCount(@JvmField val nbLines: Int,
                          @JvmField val addedLines: Int,
                          @JvmField val deletedLines: Int,
                          @JvmField val modifiedLines: Int
) {
    companion object {
        @JvmStatic
        fun from(diffLines: Array<String>, fileContent: ByteArray, fileName: String?): FileLinesCount {
            var addedLines = 0
            var deletedLines = 0
            var modifiedLines = 0
            for (line in diffLines) {
                if (!line.endsWith("/dev/null") && !line.endsWith(fileName!!)) {
                    if (line.startsWith("+") && !line.startsWith("+++")) {
                        addedLines++
                    } else if (line.startsWith("-") && !line.startsWith("---")) {
                        deletedLines++
                    } else if (line.startsWith("+++") || line.startsWith("---")) {
                        modifiedLines++
                    }
                }
            }
            val nbLines = linesNumber(fileContent)
            return FileLinesCount(nbLines, addedLines, deletedLines, modifiedLines)
        }

        private fun linesNumber(fileContent: ByteArray): Int {
            if (fileContent.size == 0) {
                return 0
            }
            val content = String(fileContent, StandardCharsets.UTF_8)
            return content.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size
        }
    }
}
