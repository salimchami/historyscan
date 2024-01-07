export class CodebaseHistoryCommitFile {
  constructor(
    public fileName: string,
    public nbLinesAdded: number,
    public nbLinesDeleted: number,
  ) {
  }
}
