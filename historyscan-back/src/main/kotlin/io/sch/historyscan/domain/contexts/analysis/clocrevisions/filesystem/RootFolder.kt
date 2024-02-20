package io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem

class RootFolder private constructor(val value: String, val codebaseName: String) {
    companion object {
        @JvmStatic
        fun of(rootFolder: String, codeBaseName: String): RootFolder {
            val actualValue = formattedValue(rootFolder, codeBaseName)
            return RootFolder(actualValue.replace("\\", "/"),
                    codeBaseName.replace("\\", "/"))
        }

        private fun formattedValue(value: String, codebaseName: String): String {
            val actualValue = value.trim { it <= ' ' }
            if (actualValue.isEmpty() || value == "/" || value == "\\" || value == codebaseName || value == "\\" + codebaseName || value == "/$codebaseName"
            ) {
                return codebaseName
            }
            return value
                    .replace("/", "\\")
                    .replace("\\\\", "\\")
        }
    }
}
