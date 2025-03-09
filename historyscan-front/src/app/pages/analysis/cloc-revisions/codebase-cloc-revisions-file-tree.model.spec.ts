import {CodebaseClocRevisionsFileTree} from "./codebase-cloc-revisions-file-tree.model";

describe('CodebaseClocRevisionsFileTree', () => {
  const name = 'groups';
  const path = 'src/iam/groups';
  const parentPath = 'src/iam';

  it('should init CodebaseClocRevisionsFileTree folder with files children', () => {
    const fileTree = CodebaseClocRevisionsFileTree.of({
      name, path, parentPath, "isFile": false, "currentNbLines": 0, "score": 30000, "children": [{
        name: 'groupe-one.ts',
        path: 'src/iam/groups/groupe-one.ts',
        parentPath: 'src/iam/groups',
        isFile: true,
        currentNbLines: 15,
        score: 15000,
        children: []
      }]
    });
    expect(fileTree).toEqual({
      name, path, parentPath, isFile: false, currentNbLines: 0, score: 30000, children: [{
        name: 'groupe-one.ts',
        path: 'src/iam/groups/groupe-one.ts',
        parentPath: 'src/iam/groups',
        isFile: true,
        currentNbLines: 15,
        score: 15000,
        children: []
      }
      ],
    } as CodebaseClocRevisionsFileTree);
  });

  it('should init CodebaseClocRevisionsFileTree folder with empty folder children', () => {
    const fileTree = CodebaseClocRevisionsFileTree.of({
      name, path, parentPath, "isFile": false, "currentNbLines": 0, "score": 0, "children": [{
        name: 'groupe-one',
        path: 'src/iam/groups/groupe-one',
        parentPath: 'src/iam/groups',
        isFile: false,
        currentNbLines: 0,
        score: 0,
        children: []
      }]
    });
    expect(fileTree).toEqual({
      name, path, parentPath, isFile: false, currentNbLines: 0, score: 0, children: [],
    } as CodebaseClocRevisionsFileTree);
  });

  it('should init CodebaseClocRevisionsFileTree file with empty children', () => {
    const fileTree = CodebaseClocRevisionsFileTree.of({
      name: 'groupe-one.ts',
      path: 'src/iam/groups/groupe-one.ts',
      parentPath: 'src/iam/groups',
      isFile: true,
      currentNbLines: 15,
      score: 15000,
      children: []
    });
    expect(fileTree).toEqual({
      name: 'groupe-one.ts',
      path: 'src/iam/groups/groupe-one.ts',
      parentPath: 'src/iam/groups',
      isFile: true, currentNbLines: 15, score: 15000, children: [],
    } as CodebaseClocRevisionsFileTree);
  });
});
