package io.sch.historyscan.domain.contexts.analysis.clocrevisions;

import java.nio.file.Paths;
import java.util.Objects;

public final class PathsUtils {

    private PathsUtils() {
        // private constructor to hide the implicit public one
    }

    public static String normalizePath(String filePath) {
        return Paths.get(filePath).normalize().toString().replace("\\", "/");
    }

    public static String normalizeFolder(String folder) {
        if(Objects.equals(folder.strip(), "/")) {
            return "/";
        }
        // delete trailing slash from start and end
        return folder.replaceAll("^/", "").replaceAll("/$", "");
    }
}
