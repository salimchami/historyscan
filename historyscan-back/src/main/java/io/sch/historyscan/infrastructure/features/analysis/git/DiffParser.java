package io.sch.historyscan.infrastructure.features.analysis.git;

import io.sch.historyscan.domain.contexts.analysis.history.CodeBaseHistoryCommitFile;
import io.sch.historyscan.domain.contexts.analysis.history.FileInfo;
import io.sch.historyscan.infrastructure.features.analysis.FileLinesCount;
import io.sch.historyscan.infrastructure.features.analysis.exceptions.CommitDiffException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class DiffParser {


    public CodeBaseHistoryCommitFile parseDiffs(Repository repository, DiffEntry fileDiff) {
        try (var out = new ByteArrayOutputStream();
             DiffFormatter formatter = new DiffFormatter(out)) {
            formatter.setRepository(repository);
            formatter.format(fileDiff);
            String diffText = out.toString();
            String[] diffLines = diffText.split("\r\n|\r|\n");
            var fileContent = fileContentFrom(repository, fileDiff.getNewPath());
            var linesCount = FileLinesCount.from(diffLines, fileContent);
            boolean isFile = fileDiff.getNewMode().equals(FileMode.REGULAR_FILE);
            return new CodeBaseHistoryCommitFile(
                    new FileInfo(Paths.get(fileDiff.getNewPath()).getFileName().toString(),
                            fileDiff.getNewPath(), isFile), linesCount.nbLines(),
                    linesCount.addedLines(), linesCount.deletedLines(), linesCount.modifiedLines());
        } catch (IOException e) {
            throw new CommitDiffException("Unable to find diff for commit", e);
        }
    }

    private byte[] fileContentFrom(Repository repository, String filePath) throws IOException {
        ObjectId objectId = repository.resolve("HEAD:%s".formatted(filePath));
        if (objectId == null) {
            return new byte[0];
        }
        ObjectLoader loader = repository.open(objectId);
        return loader.getBytes();
    }
}
