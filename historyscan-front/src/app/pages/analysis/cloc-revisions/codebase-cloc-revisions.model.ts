import {CodebaseClocRevisionsFileTree} from "./codebase-cloc-revisions-file-tree.model";
import {CodebaseClocRevisionsIgnoredFile} from "./codebase-cloc-revisions-ignored-file.model";

export class CodebaseClocRevisions {
  constructor(public node: CodebaseClocRevisionsFileTree,
              public ignoredRevisions: Array<CodebaseClocRevisionsIgnoredFile>,
              public extensions: Array<string>) {
  }

  static of(codebaseClocRevisions: any) {
    return new CodebaseClocRevisions(
      CodebaseClocRevisionsFileTree.of(codebaseClocRevisions.node),
      codebaseClocRevisions.ignoredRevisions,
      codebaseClocRevisions.extensions.sort());
  }

  copy(extensionsToKeep: Array<string>): CodebaseClocRevisions {
    return new CodebaseClocRevisions(
      CodebaseClocRevisionsFileTree.copyWithExtensions(this.node, extensionsToKeep),
      this.ignoredRevisions.map((ignoredFile) => CodebaseClocRevisionsIgnoredFile.of(ignoredFile)),
      this.extensions.map((extension) => extension)
    );
  }

  isEmpty() {
    return this.node.name === "" || this.node.children.length === 0 || this.node.path === "";
  }

  static empty() {
    return new CodebaseClocRevisions(CodebaseClocRevisionsFileTree.emptyRoot(), [], []);
  }
}
