package io.sch.historyscan.infrastructure.features.analysis;

import java.nio.charset.StandardCharsets;

public record FileLinesCount(int nbLines,
                             int addedLines,
                             int deletedLines,
                             int modifiedLines
) {

    public static FileLinesCount from(String[] diffLines, byte[] fileContent) {
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
        var nbLines = linesNumber(fileContent, fileContent.length);
        return new FileLinesCount(nbLines, addedLines, deletedLines, modifiedLines);
    }

    private static int linesNumber(byte[] fileContent, int contentLength) {
        if (fileContent.length == 0) {
            return 0;
        }
        String content = new String(fileContent, StandardCharsets.UTF_8);
        return content.split("\n").length;
    }
}
