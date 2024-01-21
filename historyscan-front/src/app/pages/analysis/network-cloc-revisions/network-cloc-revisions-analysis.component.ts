import {AfterViewInit, Component} from '@angular/core';
import {LocalstorageService} from "../../../shared/localstorage.service";
import {AnalysisService} from "../analysis.service";
import {CodebaseNetworkRevisions} from "./codebase-network-revisions.model";

@Component({
  selector: 'app-network-cloc-revisions-analysis',
  templateUrl: './network-cloc-revisions-analysis.component.html',
  styleUrls: ['./network-cloc-revisions-analysis.component.scss'],
})
export class NetworkClocRevisionsAnalysisComponent implements AfterViewInit {
  rootFolder: string = '';
  codebaseName: string = '';
  private initialCodebaseNetworkRevisions!: CodebaseNetworkRevisions;
  private codebaseNetworkRevisions!: CodebaseNetworkRevisions;

  constructor(private localStorageService: LocalstorageService,
              private readonly analysisService: AnalysisService) {
  }

  ngAfterViewInit(): void {
    Promise.resolve().then(() => {
      const filesTree = this.localStorageService.getFilesTree();
      if (filesTree) {
        console.error("not yet implemented :/");
      } else {
        this.rootFolder = this.localStorageService.getAnalysisRootFolder()!;
        this.codebaseName = "'" + this.localStorageService.getCodebaseUrl()?.split('/').pop()?.split('.')[0] + "'";
        this.initData();
      }
    })
  }

  private initData() {
    this.analysisService.networkClocAndRevisions().subscribe({
      next: (codebaseNetworkRevisions) => {
        this.initialCodebaseNetworkRevisions = CodebaseNetworkRevisions.of(codebaseNetworkRevisions);
        this.codebaseNetworkRevisions = CodebaseNetworkRevisions.of(codebaseNetworkRevisions);
        this.initRevisionsTreeMap();
        this.initExtensionsFormArray();
        this.initCanDownloadAndUpload();
      }
    });
  }

  private initRevisionsTreeMap() {

  }

  private initExtensionsFormArray() {

  }

  private initCanDownloadAndUpload() {

  }
}
