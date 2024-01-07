export class CodebaseClocRevisionsIgnoredFile {
  constructor(
    public name: string,
    public path: string) {
  }

  static of(ignoredFile: any) {
    return new CodebaseClocRevisionsIgnoredFile(
      ignoredFile.name,
      ignoredFile.path);
  }
}
