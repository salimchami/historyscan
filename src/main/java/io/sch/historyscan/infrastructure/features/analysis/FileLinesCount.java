package io.sch.historyscan.infrastructure.features.analysis;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public record FileLinesCount(int nbLines,
                             int addedLines,
                             int deletedLines,
                             int modifiedLines
) {

    public static FileLinesCount from(String[] diffLines, Repository repository, String filePath) throws IOException {
        int addedLines = 0;
        int deletedLines = 0;
        int modifiedLines = 0;
        for (String line : diffLines) {
            if (line.startsWith("+") && !line.startsWith("+++")) {
                addedLines++;

            } else if (line.startsWith("-") && !line.startsWith("---")) {
                deletedLines++;
            } else if (line.startsWith("+++") || line.startsWith("---")) {
                modifiedLines++;
            }
        }
        var nbLines = linesNumbedr(repository, filePath);
        return new FileLinesCount(nbLines, addedLines, deletedLines, modifiedLines);
    }

    private static int linesNumbedr(Repository repository, String filePath) throws IOException {
        ObjectId objectId = repository.resolve(filePath);
        ObjectLoader loader = repository.open(objectId);
        byte[] bytes = loader.getBytes();
        String content = new String(bytes, StandardCharsets.UTF_8);
        return content.split("\n").length;
    }
}
