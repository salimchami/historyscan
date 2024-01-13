import {AfterContentInit, Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CodebasesService} from "./codebases.service";
import {CurrentCodebase} from "./current-codebase.model";
import {MatTableDataSource} from "@angular/material/table";
import {LocalstorageService} from "../../shared/localstorage.service";
import {MatDialog} from "@angular/material/dialog";
import {RootFolderDialog} from "./root-folder-dialog/root-folder-dialog";

@Component({
  selector: 'app-codebases',
  templateUrl: './codebases.component.html',
  styleUrls: ['./codebases.component.scss'],
})
export class CodebasesComponent implements AfterContentInit {

  displayedColumns = ['name', 'url', 'currentBranch', 'actions'];
  dataSource: MatTableDataSource<CurrentCodebase> = new MatTableDataSource<CurrentCodebase>([]);

  constructor(private readonly activatedRoute: ActivatedRoute,
              private readonly codebasesService: CodebasesService,
              private readonly router: Router,
              private readonly localStorageService: LocalstorageService,
              public dialog: MatDialog) {
  }

  ngAfterContentInit(): void {
    this.activatedRoute.paramMap.subscribe(() => {
      this.codebasesService.getCodebases()
        .subscribe((codebases) => {
          this.dataSource = new MatTableDataSource<CurrentCodebase>(codebases.codebases);
        });
    });
  }

  hasClocAndRevisions(element: CurrentCodebase): boolean {
    return !!element._links['analyze-cloc-revisions']?.href;
  }

  hasNetworkClocAndRevisions(element: CurrentCodebase): boolean {
    return !!element._links['analyze-network-cloc-revisions']?.href;
  }

  clocAndRevisions(element: CurrentCodebase): void {
    const dialogRef = this.dialog.open(RootFolderDialog);
    dialogRef.afterClosed().subscribe(rootFolder => {
      if (rootFolder) {
        this.addToLocalStorage(element, rootFolder, 'analyze-cloc-revisions');
       this.localStorageService.clearFilesTree()
        this.router.navigateByUrl('/analysis/cloc-revisions').then();
      }
    });
  }

  networkClocAndRevisions(element: CurrentCodebase): void {
    this.addToLocalStorage(element, '', 'analyze-network-cloc-revisions');
    this.router.navigateByUrl('/analysis/network-cloc-revisions').then();
  }

  private addToLocalStorage(element: CurrentCodebase, rootFolder: string, analysisType: string) {
    this.localStorageService.clearAllItems();
    this.localStorageService.addItems(
      element,
      rootFolder,
      element._links[analysisType]?.type,
      element._links[analysisType]?.href);
  }
}
