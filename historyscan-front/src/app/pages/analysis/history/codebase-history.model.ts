import {CodebaseHistoryFile} from "./codebase-history-file.model";

export class CodebaseHistory {
  constructor(
    public files: Array<CodebaseHistoryFile>
  ) {

  }
}
