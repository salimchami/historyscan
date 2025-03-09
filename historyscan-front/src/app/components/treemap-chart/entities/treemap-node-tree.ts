import {
  CodebaseClocRevisionsFileTree
} from "../../../pages/analysis/cloc-revisions/codebase-cloc-revisions-file-tree.model";

export class TreemapNodeTree {
  private constructor(
    readonly name: string,
    readonly value: number,
    readonly path: string,
    readonly isFile: boolean,
    readonly dataIndex: number,
    readonly children: TreemapNodeTree[] = [],
  ) {
  }

  static handle(name: string, value: number, path: string, isFile: boolean, dataIndex: number, children: TreemapNodeTree[]): TreemapNodeTree {
    return new TreemapNodeTree(name, value, path, isFile, dataIndex, children);
  }

  static from(revisions: CodebaseClocRevisionsFileTree): TreemapNodeTree {
    return TreemapNodeTree.handle('root', revisions.score, '/', false, 1, TreemapNodeTree.generateChildren(revisions.children));
  }

  private static generateChildren(revisions: CodebaseClocRevisionsFileTree[], indexRef: {
    current: number
  } = {current: 1}): TreemapNodeTree[] {
    const nodeChildren = [];
    for (const revision of revisions) {
      if (revision.isFile || (!revision.isFile && !!revision.children.length)) {
        const currentIndex = indexRef.current++;
        nodeChildren.push(TreemapNodeTree.handle(
          revision.name,
          revision.score,
          revision.path,
          revision.isFile,
          currentIndex,
          TreemapNodeTree.generateChildren(revision.children, indexRef)
        ));
      }
    }
    return nodeChildren;
  }
}
