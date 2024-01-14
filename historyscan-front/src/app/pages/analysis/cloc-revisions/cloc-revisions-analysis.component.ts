import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AnalysisService} from "./analysis.service";
import {CodebaseClocRevisions} from "./codebase-cloc-revisions.model";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {LocalstorageService} from "../../../shared/localstorage.service";
import {TreemapChartComponent} from "../../../components/treemap-chart/treemap-chart.component";
import {ClocRevisionsService} from "./cloc-revisions.service";
import {DownloadCodebaseClocrevisionsFileTree} from "./download-codebase-clocrevisions-file-tree.model";
import {MatDialog} from "@angular/material/dialog";
import {ClocRevisionsTreeUploadDialogComponent} from "./upload-dialog/cloc-revisions-tree-upload-dialog.component";
import {TranslateService} from "@ngx-translate/core";
import {CodebaseClocRevisionsFileTree} from "./codebase-cloc-revisions-file-tree.model";

@Component({
  selector: 'app-cloc-revisions-analysis',
  templateUrl: './cloc-revisions-analysis.component.html',
  styleUrls: ['./cloc-revisions-analysis.component.scss'],
})
export class ClocRevisionsAnalysisComponent implements OnInit, AfterViewInit {
  @ViewChild('treemapChart') treemapChart!: TreemapChartComponent;
  analysisForm: FormGroup;
  initialCodebaseClocRevisions: CodebaseClocRevisions = CodebaseClocRevisions.empty();
  codebaseClocRevisions: CodebaseClocRevisions = CodebaseClocRevisions.empty();
  showSearchHint: boolean = false;
  fileUploaded = false;
  rootFolder: string = '';
  codebaseName: string = '';
  canUpload = false;
  canDownload = false;

  constructor(private readonly activatedRoute: ActivatedRoute,
              private readonly analysisService: AnalysisService,
              private readonly fb: FormBuilder,
              private readonly localStorageService: LocalstorageService,
              private readonly clocRevisionsService: ClocRevisionsService,
              private readonly dialog: MatDialog,
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
        this.loadUploadedFile(filesTree, false);
        this.translate.get('foo')
          .subscribe(() => this.codebaseName = this.translate.instant('global.pages.analysis.cloc-revisions.title-uploaded-file'));
        this.fileUploaded = true;
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

  private initCanDownloadAndUpload() {
    this.canUpload = true;
    this.canDownload = this.canUpload && !this.fileUploaded;
  }

  private initData() {
    this.activatedRoute.paramMap.subscribe(() => {
      this.analysisService.clocAndRevisions().subscribe({
        next: (codebaseClocRevisions) => {
          this.initialCodebaseClocRevisions = CodebaseClocRevisions.of(codebaseClocRevisions);
          this.codebaseClocRevisions = CodebaseClocRevisions.of(codebaseClocRevisions);
          this.initRevisionsTreeMap();
          this.initExtensionsFormArray();
          this.initCanDownloadAndUpload();
        }
      });
    });
  }

  private initRevisionsTreeMap() {
    if (!this.codebaseClocRevisions.isEmpty()) {
      this.treemapChart.updateChartSeries(
        this.codebaseClocRevisions.node,
        this.localStorageService.getCodebaseUrl()!,
        this.localStorageService.getCodebaseBranch()!,
      );
    }
  }

  private initExtensionsFormArray() {
    this.codebaseClocRevisions.extensions.forEach(() => {
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
        extensionsToKeep.push(this.codebaseClocRevisions.extensions[i]);
      }
    }
    this.codebaseClocRevisions = this.initialCodebaseClocRevisions.copy(extensionsToKeep);
    this.initRevisionsTreeMap();
  }

  unselectAll() {
    this.select(false);
  }

  selectAll() {
    this.select(true);
  }

  disabledButtons(): boolean {
    return this.codebaseClocRevisions?.extensions?.length === 0;
  }

  containsOneExtension(): boolean {
    debugger;
    return this.codebaseClocRevisions?.extensions?.length === 1;
  }

  private select(value: boolean) {
    this.extensionsFormArray.controls.forEach((control) => {
      control.setValue(value);
    });
  }

  private onSearchType() {
    this.analysisForm.get('search')?.valueChanges.subscribe((targetItem: string) => {
      if (targetItem && targetItem.length > 3) {
        this.treemapChart.tooltipOn(targetItem);
      }
    });
  }

  download() {
    this.clocRevisionsService.download(
      new DownloadCodebaseClocrevisionsFileTree(
        this.localStorageService.getCodebaseUrl()!,
        this.localStorageService.getCodebaseBranch()!,
        this.codebaseClocRevisions.node));
  }

  upload() {
    this.dialog.open(ClocRevisionsTreeUploadDialogComponent, {
      width: '50%',
    }).afterClosed().subscribe((result) => {
      if (result) {
        this.loadUploadedFile(result, true);
      }
    });
  }

  private loadUploadedFile(result: CodebaseClocRevisionsFileTree, updateFileStorage: boolean) {
    this.localStorageService.clearCodebaseUrl();
    this.localStorageService.clearCodebaseBranch();
    if (updateFileStorage) {
      this.localStorageService.addFilesTree(result);
    }
    this.initialCodebaseClocRevisions = new CodebaseClocRevisions(result, [], []);
    this.codebaseClocRevisions = new CodebaseClocRevisions(result, [], []);
    this.fileUploaded = true;
    this.codebaseName = this.translate.instant('global.pages.analysis.cloc-revisions.title-uploaded-file');
    this.rootFolder = '';
    this.initCanDownloadAndUpload();
    this.initRevisionsTreeMap();

  }
}
