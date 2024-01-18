package io.sch.historyscan.infrastructure.features.analysis;

import java.nio.charset.StandardCharsets;

public record FileLinesCount(int nbLines,
                             int addedLines,
                             int deletedLines,
                             int modifiedLines
) {

    public static FileLinesCount from(String[] diffLines, byte[] fileContent, String fileName) {
        int addedLines = 0;
        int deletedLines = 0;
        int modifiedLines = 0;
        for (String line : diffLines) {
            if (!line.endsWith("/dev/null") && !line.endsWith(fileName)) {
                if (line.startsWith("+") && !line.startsWith("+++")) {
                    addedLines++;
                } else if (line.startsWith("-") && !line.startsWith("---")) {
                    deletedLines++;
                } else if (line.startsWith("+++") || line.startsWith("---")) {
                    modifiedLines++;
                }
            }
        }
        var nbLines = linesNumber(fileContent);
        return new FileLinesCount(nbLines, addedLines, deletedLines, modifiedLines);
    }

    private static int linesNumber(byte[] fileContent) {
        if (fileContent.length == 0) {
            return 0;
        }
        String content = new String(fileContent, StandardCharsets.UTF_8);
        return content.split("\n").length;
    }
}
