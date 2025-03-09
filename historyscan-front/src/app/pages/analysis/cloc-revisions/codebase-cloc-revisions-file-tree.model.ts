export class CodebaseClocRevisionsFileTree {
  private constructor(
    public name: string,
    public path: string,
    public parentPath: string,
    public isFile: boolean,
    public currentNbLines: number,
    public score: number,
    public children: Array<CodebaseClocRevisionsFileTree>) {
  }

  static of(fileTree: any): CodebaseClocRevisionsFileTree {
    if (!fileTree.isFile && fileTree.children.length === 0) {
      throw new Error(`Folder node has empty children: ${JSON.stringify(fileTree, null, 2)}`);
    }
    return new CodebaseClocRevisionsFileTree(
      fileTree.name,
      fileTree.path,
      fileTree.parentPath,
      fileTree.isFile,
      fileTree.currentNbLines,
      fileTree.score,
      CodebaseClocRevisionsFileTree.revisionFileTreeChildren(fileTree)
    );
  }

  private static revisionFileTreeChildren(fileTree: any): CodebaseClocRevisionsFileTree[] {
    if (fileTree.isFile) {
      return [];
    }
    const children: CodebaseClocRevisionsFileTree[] = [];
    for (const child of fileTree.children) {
      if (child.isFile || (!child.isFile && !!child.children.length)) {
        children.push(CodebaseClocRevisionsFileTree.of(child))
      }
    }
    return children;
  }

  static copyWithExtensions(fileTree: CodebaseClocRevisionsFileTree, extensionsToKeep: Array<string>) {
    const children: Array<CodebaseClocRevisionsFileTree> = fileTree.children
      .map((child: CodebaseClocRevisionsFileTree) => CodebaseClocRevisionsFileTree.copyWithExtensions(child, extensionsToKeep))
      .filter((x): x is CodebaseClocRevisionsFileTree => x.name !== "")
      .flatMap(f => f ? [f] : []);

    if (fileTree.isFile) {
      let extension = fileTree.name.split('.').pop();
      if (!extensionsToKeep.includes(extension!)) {
        return CodebaseClocRevisionsFileTree.emptyRoot();
      }
    }
    if (!fileTree.isFile && children.length === 0) {
      return CodebaseClocRevisionsFileTree.emptyRoot();
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
