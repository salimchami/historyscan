import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AnalysisService} from "../../codebases/analysis.service";
import {CodebaseClocRevisions} from "./codebase-cloc-revisions.model";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {LocalstorageService} from "../../../shared/localstorage.service";
import {CodebaseClocRevisionsFileTree} from "./codebase-cloc-revisions-file-tree.model";
import {TreemapChartComponent} from "../../../components/treemap-chart/treemap-chart.component";

@Component({
  selector: 'app-cloc-revisions-analysis',
  templateUrl: './cloc-revisions-analysis.component.html',
  styleUrls: ['./cloc-revisions-analysis.component.scss'],
})
export class ClocRevisionsAnalysisComponent implements OnInit {
  @ViewChild('treemapChart') treemapChart!: TreemapChartComponent;
  analysisForm: FormGroup;
  initialCodebaseClocRevisions: CodebaseClocRevisions = CodebaseClocRevisions.empty();
  codebaseClocRevisions: CodebaseClocRevisions = CodebaseClocRevisions.empty();
  showSearchHint: boolean = false;

  constructor(private readonly activatedRoute: ActivatedRoute,
              private readonly analysisService: AnalysisService,
              private readonly fb: FormBuilder,
              private readonly localStorageService: LocalstorageService) {
    this.analysisForm = this.fb.group({
      extensions: this.fb.array([]),
      search: [''],
    });
  }

  ngOnInit(): void {
    this.initData();
    this.onSearchType();
  }

  private initData() {
    this.activatedRoute.paramMap.subscribe(() => {
      this.analysisService.clocAndRevisions().subscribe({
        next: (codebaseClocRevisions) => {
          this.initialCodebaseClocRevisions = CodebaseClocRevisions.of(codebaseClocRevisions);
          this.codebaseClocRevisions = CodebaseClocRevisions.of(codebaseClocRevisions);
          this.initRevisionsTreeMap();
          this.initExtensionsFormArray();
        }
      });
    });
  }

  initRevisionsTreeMap() {
    if (!this.codebaseClocRevisions.isEmpty()) {
      this.treemapChart.updateChartSeries(
        this.codebaseClocRevisions.node,
        this.localStorageService.getItem('codebase-url')!,
        this.localStorageService.getItem('codebase-branch')!,
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

  disabledButtons() {
    return this.codebaseClocRevisions?.extensions?.length === 0;
  }

  private select(value: boolean) {
    this.extensionsFormArray.controls.forEach((control) => {
      control.setValue(value);
    });
  }

  private onSearchType() {
    this.analysisForm.get('search')?.valueChanges.subscribe((targetItem: string) => {
      if (targetItem && targetItem.length > 3) {
        this.treemapChart.zoomOn(targetItem);
      }
    });
  }
}
