import {CodebaseClocRevisionsFileTree} from "./codebase-cloc-revisions-file-tree.model";

export class DownloadCodebaseClocrevisionsFileTree {
  constructor(
    public codebaseUrl: string,
    public codebaseBranch: string,
    public payload: CodebaseClocRevisionsFileTree) {

  }

}
