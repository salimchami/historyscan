import {AfterViewInit, Component} from '@angular/core';
import {LocalstorageService} from "../../../shared/localstorage.service";
import {CodebaseClocRevisions} from "../cloc-revisions/codebase-cloc-revisions.model";
import {AnalysisService} from "../analysis.service";

@Component({
  selector: 'app-network-cloc-revisions-analysis',
  templateUrl: './network-cloc-revisions-analysis.component.html',
  styleUrls: ['./network-cloc-revisions-analysis.component.scss'],
})
export class NetworkClocRevisionsAnalysisComponent implements AfterViewInit {
  rootFolder: string = '';
  codebaseName: string = '';

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
      next: (codebaseClocRevisions) => {
        this.initialCodebaseClocRevisions = CodebaseClocRevisions.of(codebaseClocRevisions);
        this.codebaseClocRevisions = CodebaseClocRevisions.of(codebaseClocRevisions);
        this.initRevisionsTreeMap();
        this.initExtensionsFormArray();
        this.initCanDownloadAndUpload();
      }
    });
  }
}



