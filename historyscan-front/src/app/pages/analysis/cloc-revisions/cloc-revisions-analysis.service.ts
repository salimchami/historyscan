import {Injectable} from "@angular/core";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {
  ClocRevisionsTreeDownloadDialogComponent
} from "./download-dialog/cloc-revisions-tree-download-dialog.component";
import {DownloadCodebaseClocrevisionsFileTree} from "./download-codebase-clocrevisions-file-tree.model";

@Injectable({providedIn: 'root'})
export class ClocRevisionsAnalysisService {

  constructor(private readonly dialog: MatDialog) {
  }

  download(fileTreeToDownload: DownloadCodebaseClocrevisionsFileTree) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {chartToDownload: fileTreeToDownload};
    this.dialog.open(ClocRevisionsTreeDownloadDialogComponent, dialogConfig);
  }
}
