export class CodebaseClocRevisionsFileTree {
  constructor(
    public name: string,
    public path: string,
    public parentPath: string,
    public isFile: boolean,
    public currentNbLines: number,
    public score: number,
    public children: Array<CodebaseClocRevisionsFileTree>) {
  }

  static of(fileTree: any) {
    return new CodebaseClocRevisionsFileTree(
      fileTree.name,
      fileTree.path,
      fileTree.parentPath,
      fileTree.file,
      fileTree.currentNbLines,
      fileTree.score,
      fileTree.children.map((child: any) => CodebaseClocRevisionsFileTree.of(child))
    );
  }

  static copyWithExtensions(fileTree: CodebaseClocRevisionsFileTree, extensionsToKeep: Array<string>) {
    const children: Array<CodebaseClocRevisionsFileTree> = fileTree.children
      .map((child: CodebaseClocRevisionsFileTree) => CodebaseClocRevisionsFileTree.copyWithExtensions(child, extensionsToKeep))
      .filter((x): x is CodebaseClocRevisionsFileTree => x.name !== "");

    if (fileTree.isFile) {
      let extension = fileTree.name.split('.').pop();
      if (!extensionsToKeep.includes(extension!)) {
        return CodebaseClocRevisionsFileTree.emptyRoot();
      }
    }
    return new CodebaseClocRevisionsFileTree(
      fileTree.name,
      fileTree.path,
      fileTree.parentPath,
      fileTree.isFile,
      fileTree.currentNbLines,
      fileTree.score,
      children
    );
  }

  static emptyRoot() {
    return new CodebaseClocRevisionsFileTree("", "/", "/", false, 0, 0, []);
  }
}
