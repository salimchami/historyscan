import {CodebaseHistoryCommitInfo} from "./codebase-history-commit-info.model";
import {CodebaseHistoryCommitFile} from "./codebase-history-commit-file.model";

export class CodebaseHistoryFile {

  constructor(
    public info: CodebaseHistoryCommitInfo,
    public files: Array<CodebaseHistoryCommitFile>
  ) {
  }
}
