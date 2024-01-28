import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {LocalstorageService} from "../../../shared/localstorage.service";
import {AnalysisService} from "../analysis.service";
import {CodebaseNetworkRevisions} from "./codebase-network-revisions.model";
import {NetworkChartComponent} from "../../../components/network-chart/network-chart.component";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {NetworkAnalysisService} from "./network-analysis.service";
import {DownloadCodebaseNetwork} from "./download-codebase-network.model";
import {MatDialog} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {NetworkNodes} from "./network-nodes.model";
import {NetworkUploadDialogComponent} from "./upload-dialog/network-upload-dialog.component";

@Component({
  selector: 'app-network-analysis',
  templateUrl: './network-cloc-revisions-analysis.component.html',
  styleUrls: ['./network-cloc-revisions-analysis.component.scss'],
})
export class NetworkClocRevisionsAnalysisComponent implements OnInit, AfterViewInit {
  @ViewChild('networkChart') networkChart!: NetworkChartComponent;
  analysisForm: FormGroup;
  rootFolder: string = '';
  codebaseName: string = '';
  fileUploaded = false;
  canUpload = false;
  canDownload = false;
  showSearchHint: boolean = false;
  private initialCodebaseNetworkRevisions = CodebaseNetworkRevisions.empty();
  codebaseNetworkRevisions = CodebaseNetworkRevisions.empty();

  constructor(private localStorageService: LocalstorageService,
              private readonly networkService: NetworkAnalysisService,
              private readonly fb: FormBuilder,
              private readonly dialog: MatDialog,
              private readonly analysisService: AnalysisService,
              private readonly translate: TranslateService) {
    this.analysisForm = this.fb.group({
      extensions: this.fb.array([]),
      search: [''],
    });
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

  ngOnInit(): void {
    this.onSearchType();
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
    if (!this.codebaseNetworkRevisions.isEmpty()) {
      this.networkChart.updateChartSeries(
        this.codebaseNetworkRevisions.network,
        this.codebaseNetworkRevisions.extensions,
        this.localStorageService.getCodebaseUrl()!,
        this.localStorageService.getCodebaseBranch()!,
      );
    }
  }

  private initExtensionsFormArray() {
    this.codebaseNetworkRevisions.extensions.forEach(() => {
      this.extensionsFormArray.push(this.fb.control(true));
    });

  }

  get extensionsFormArray() {
    return this.analysisForm.get('extensions') as FormArray;
  }

  onRefresh() {
    this.updateDataSources(this.extensionsFormArray.value);
  }

  private updateDataSources(extensions: Array<boolean>) {
    const extensionsToKeep: Array<string> = [];
    for (let i = 0; i < extensions.length; i++) {
      if (extensions[i]) {
        extensionsToKeep.push(this.codebaseNetworkRevisions.extensions[i]);
      }
    }
    this.codebaseNetworkRevisions = this.initialCodebaseNetworkRevisions.copy(extensionsToKeep);
    this.initRevisionsTreeMap();
  }

  unselectAll() {
    this.select(false);
  }

  selectAll() {
    this.select(true);
  }

  disabledButtons(): boolean {
    return this.codebaseNetworkRevisions?.extensions?.length === 0;
  }

  containsOneExtension(): boolean {
    return this.codebaseNetworkRevisions?.extensions?.length === 1;
  }

  private select(value: boolean) {
    this.extensionsFormArray.controls.forEach((control) => {
      control.setValue(value);
    });
  }

  private onSearchType() {
    this.analysisForm.get('search')?.valueChanges.subscribe((targetItem: string) => {
      if (targetItem && targetItem.length > 3) {
        this.networkChart.tooltipOn(targetItem);
      }
    });
  }

  download() {
    this.networkService.download(
      new DownloadCodebaseNetwork(
        this.localStorageService.getCodebaseUrl()!,
        this.localStorageService.getCodebaseBranch()!,
        this.codebaseNetworkRevisions.network));
  }

  upload() {
    this.dialog.open(NetworkUploadDialogComponent, {
      width: '50%',
    }).afterClosed().subscribe((result) => {
      if (result) {
        this.loadUploadedFile(result, true);
      }
    });
  }

  private loadUploadedFile(result: NetworkNodes, updateFileStorage: boolean) {
    this.localStorageService.clearCodebaseUrl();
    this.localStorageService.clearCodebaseBranch();
    if (updateFileStorage) {
      this.localStorageService.addFilesTree(result);
    }
    this.initialCodebaseNetworkRevisions = new CodebaseNetworkRevisions(result, [], []);
    this.codebaseNetworkRevisions = new CodebaseNetworkRevisions(result, [], []);
    this.fileUploaded = true;
    this.codebaseName = this.translate.instant('global.pages.analysis.cloc-revisions.title-uploaded-file');
    this.rootFolder = '';
    this.initCanDownloadAndUpload();
    this.initRevisionsTreeMap();
  }

  private initCanDownloadAndUpload() {
    this.canUpload = true;
    this.canDownload = this.canUpload && !this.fileUploaded;
  }
}
