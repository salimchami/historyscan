package io.sch.historyscan.domain.contexts.analysis.common;

import io.sch.historyscan.domain.contexts.analysis.clocrevisions.filesystem.FilePath;

import java.util.Arrays;

import static io.sch.historyscan.domain.contexts.analysis.common.EnumPathType.FILE;
import static io.sch.historyscan.domain.contexts.analysis.common.EnumPathType.FOLDER;

public enum EnumIgnoredCodeBaseFiles {
    DEV_NULL("/dev/null", FILE),
    PACKAGE_LOCK_JSON("package-lock.json", FILE),
    YARN_LOCK("yarn.lock", FILE),
    LIBS("libs", FOLDER),
    LIB("lib", FOLDER),
    JS_MAP("js.map", FILE),
    MAP("map", FILE),
    NOD_MODULES("node_modules", FOLDER),
    DIST("dist", FOLDER),
    BUILD("build", FOLDER),
    CACHE(".cache", FILE),
    DOT_TEMP(".temp", FILE),
    TEMP("temp", FOLDER),
    COVERAGE("coverage", FOLDER),
    DOCS("docs", FOLDER),
    D_TS(".d.ts", FILE),
    RELEASE("release", FOLDER),
    CLASS(".class", FILE),
    JAR(".jar", FILE),
    BIN_FOLDER("bin", FOLDER),
    BIN_FILE("bin", FILE),
    TARGET("target", FOLDER),
    OUT("out", FOLDER),
    GENERATED("generated", FOLDER),
    GRADLEW("gradlew", FILE),
    DOT_GRADLE(".gradle", FOLDER),
    GRADLE("gradle", FOLDER),
    GITATTRIBUTES("gitattributes", FILE),
    DOCUMENTATION("documentation", FOLDER),
    PACKAGE_INFO_JAVA("package-info.java", FILE),
    IDEA_INTELLIJ(".idea", FOLDER),
    HTML_REPORT("htmlreport", FOLDER),
    RUN("run", FOLDER),
    ;

    private final String title;
    private final EnumPathType type;

    EnumIgnoredCodeBaseFiles(String title, EnumPathType type) {
        this.title = title;
        this.type = type;
    }

    public static boolean isIgnored(String codebasesFolder, String path, boolean isFile) {
        var commonPart = FilePath.commonPartOf(codebasesFolder, path);
        var filteredPath = path.substring(commonPart.length());
        return Arrays.stream(values()).anyMatch(ignoredFile -> isIgnored(filteredPath, isFile, ignoredFile));
    }


    private static boolean isIgnored(String path, boolean isFile, EnumIgnoredCodeBaseFiles ignoredFile) {
        if (path.isEmpty()) {
            return false;
        }
        final String formattedPath = path.replace("\\", "/").trim().toLowerCase();
        if (isFile) {
            return (ignoredFile.type == FOLDER && formattedPath.contains(ignoredFile.title + "/"))
                   || formattedPath.substring(formattedPath.lastIndexOf("/") + 1)
                              .equals(ignoredFile.title) && ignoredFile.type == FILE;
        } else {
            return formattedPath.contains(ignoredFile.title + "/")
                   || formattedPath.contains("/" + ignoredFile.title)
                   || ignoredFile.title.contains(formattedPath);
        }
    }
}
