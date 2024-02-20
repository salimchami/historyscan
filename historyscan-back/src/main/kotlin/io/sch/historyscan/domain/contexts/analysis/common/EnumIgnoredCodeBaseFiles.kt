package io.sch.historyscan.domain.contexts.analysis.common

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.*
import java.util.*

enum class EnumIgnoredCodeBaseFiles(private val title: String, private val type: EnumPathType) {
    DEV_NULL("/dev/null", EnumPathType.FILE),
    PACKAGE_LOCK_JSON("package-lock.json", EnumPathType.FILE),
    YARN_LOCK("yarn.lock", EnumPathType.FILE),
    LIBS("libs", EnumPathType.FOLDER),
    LIB("lib", EnumPathType.FOLDER),
    JS_MAP("js.map", EnumPathType.FILE),
    MAP("map", EnumPathType.FILE),
    NOD_MODULES("node_modules", EnumPathType.FOLDER),
    DIST("dist", EnumPathType.FOLDER),
    BUILD("build", EnumPathType.FOLDER),
    CACHE(".cache", EnumPathType.FILE),
    DOT_TEMP(".temp", EnumPathType.FILE),
    TEMP("temp", EnumPathType.FOLDER),
    COVERAGE("coverage", EnumPathType.FOLDER),
    DOCS("docs", EnumPathType.FOLDER),
    D_TS(".d.ts", EnumPathType.FILE),
    RELEASE("release", EnumPathType.FOLDER),
    CLASS(".class", EnumPathType.FILE),
    JAR(".jar", EnumPathType.FILE),
    BIN_FOLDER("bin", EnumPathType.FOLDER),
    BIN_FILE("bin", EnumPathType.FILE),
    TARGET("target", EnumPathType.FOLDER),
    OUT("out", EnumPathType.FOLDER),
    GENERATED("generated", EnumPathType.FOLDER),
    GRADLEW("gradlew", EnumPathType.FILE),
    DOT_GRADLE(".gradle", EnumPathType.FOLDER),
    GRADLE("gradle", EnumPathType.FOLDER),
    GITATTRIBUTES("gitattributes", EnumPathType.FILE),
    DOCUMENTATION("documentation", EnumPathType.FOLDER),
    PACKAGE_INFO_JAVA("package-info.java", EnumPathType.FILE),
    IDEA_INTELLIJ(".idea", EnumPathType.FOLDER),
    HTML_REPORT("htmlreport", EnumPathType.FOLDER),
    RUN("run", EnumPathType.FOLDER),
    ;

    companion object {
        @JvmStatic
        fun isIgnored(codebasesFolder: String, path: String, isFile: Boolean): Boolean {
            val commonPart: String = FilePath.commonPartOf(codebasesFolder, path)
            val filteredPath = path.substring(commonPart.length)
            return Arrays.stream(entries.toTypedArray()).anyMatch { ignoredFile: EnumIgnoredCodeBaseFiles -> isIgnored(filteredPath, isFile, ignoredFile) }
        }

        private fun isIgnored(path: String, isFile: Boolean, ignoredFile: EnumIgnoredCodeBaseFiles): Boolean {
            if (path.isEmpty()) {
                return false
            }
            val formattedPath = path.replace("\\", "/").trim { it <= ' ' }.lowercase(Locale.getDefault())
            return if (isFile) {
                ((ignoredFile.type == EnumPathType.FOLDER && formattedPath.contains(ignoredFile.title + "/"))
                        || (formattedPath.substring(formattedPath.lastIndexOf("/") + 1)
                        == ignoredFile.title) && ignoredFile.type == EnumPathType.FILE)
            } else {
                (formattedPath.contains(ignoredFile.title + "/")
                        || formattedPath.contains("/" + ignoredFile.title)
                        || ignoredFile.title.contains(formattedPath))
            }
        }
    }
}
