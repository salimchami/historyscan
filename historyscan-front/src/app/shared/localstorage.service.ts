import {Injectable} from "@angular/core";
import {CodebaseClocRevisionsFileTree} from "../pages/analysis/cloc-revisions/codebase-cloc-revisions-file-tree.model";

@Injectable({
  providedIn: 'root'
})
export class LocalstorageService {
  private readonly CODEBASES_URL = 'codebases-url';
  private readonly CODEBASE_BRANCH = 'codebase-branch';
  private readonly CODEBASE_URL = 'codebase-url';
  private readonly ANALYSIS_ROOT_FOLDER = 'analysis-root-folder';
  private readonly ANALYSIS_HREF = 'analysis-href';
  private readonly ANALYSIS_NAME = 'analysis-name';
  private readonly ANALYSIS_TYPE = 'analysis-type';
  private readonly FILES_TREE = 'files-tree';
  private readonly NETWORK_FILES_TREE = 'network-files-tree';

  private addItemsToLocalStorage(items: Array<{ key: string, value: string }>) {
    items.forEach(item => this.addItem(item.key, item.value));
  }

  private removeItem(key: string) {
    localStorage.removeItem(key);
  }

  private removeItems(keys: Array<string>) {
    keys.forEach(key => this.removeItem(key));
  }

  private addItem(key: string, value: string): void {
    localStorage.setItem(key, value);
  }

  getCodebasesUrl() {
    return localStorage.getItem(this.CODEBASES_URL);
  }

  getCodebaseBranch() {
    return localStorage.getItem(this.CODEBASE_BRANCH);
  }

  getCodebaseUrl() {
    return localStorage.getItem(this.CODEBASE_URL);
  }

  getAnalysisRootFolder() {
    return localStorage.getItem(this.ANALYSIS_ROOT_FOLDER);
  }

  clearAllItems() {
    this.removeItems([this.ANALYSIS_HREF, this.ANALYSIS_NAME, this.ANALYSIS_TYPE,
      this.ANALYSIS_ROOT_FOLDER, this.CODEBASE_URL, this.CODEBASE_BRANCH]);
  }

  addItems(element: any, rootFolder?: string, analysisType?: string, analysisHref?: string) {
    let items = [
      {key: this.ANALYSIS_NAME, value: element.name},
      {key: this.CODEBASE_URL, value: element.url},
      {key: this.CODEBASE_BRANCH, value: element.currentBranch}
    ]
    if (analysisType) {
      const type = [{key: this.ANALYSIS_HREF, value: analysisHref},
        {key: this.ANALYSIS_TYPE, value: analysisType}];
      items = items.concat(type);

    }
    if (rootFolder) {
      items = items.concat([{key: this.ANALYSIS_ROOT_FOLDER, value: rootFolder}]);
    }
    this.addItemsToLocalStorage(items);
  }

  getAnalysisHref() {
    return localStorage.getItem(this.ANALYSIS_HREF);
  }

  getAnalysisName() {
    return localStorage.getItem(this.ANALYSIS_NAME);
  }

  getAnalysisType() {
    return localStorage.getItem(this.ANALYSIS_TYPE);
  }

  addCodebasesUrl(href: any) {
    this.addItem(this.CODEBASES_URL, href);
  }

  clearCodebaseUrl() {
    this.removeItem(this.CODEBASE_URL);
  }

  clearCodebaseBranch() {
    this.removeItem(this.CODEBASE_BRANCH);
  }

  getFilesTree() {
    const item = localStorage.getItem(this.FILES_TREE);
    if (item) {
      return CodebaseClocRevisionsFileTree.of(JSON.parse(item));
    }
    return null;
  }

  addFilesTree(result: any) {
    this.addItem(this.FILES_TREE, JSON.stringify(result));
  }

  clearFilesTree() {
    this.removeItem(this.FILES_TREE);
  }

  clearNetworkFilesTree() {
    this.removeItem(this.NETWORK_FILES_TREE);
  }
}
