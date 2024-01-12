import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {CodebaseClocRevisionsFileTree} from "../codebase-cloc-revisions-file-tree.model";
import {MaterialModule, SharedModule} from "../../../../shared";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'cloc-revisions-tree-download-dialog',
  templateUrl: 'cloc-revisions-tree-download-dialog.component.html',
  styleUrls: ['cloc-revisions-tree-download-dialog.component.scss'],
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MaterialModule,
    MatCardModule,
    CommonModule,
    SharedModule
  ],
})
export class ClocRevisionsTreeDownloadDialogComponent {

  codebaseUrl: any;
  codebaseBranch: any;
  jsonPayload: string;
  filename: string = '';
  displayJson: boolean = false;
  sanitizedBlobUrl!: SafeUrl;
  private readonly payload: CodebaseClocRevisionsFileTree;

  constructor(
    public dialogRef: MatDialogRef<ClocRevisionsTreeDownloadDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private sanitizer: DomSanitizer
  ) {
    this.payload = data.fileTreeToDownload.payload;
    this.codebaseUrl = data.fileTreeToDownload.codebaseUrl;
    this.codebaseBranch = data.fileTreeToDownload.codebaseBranch;
    this.jsonPayload = JSON.stringify(this.payload, null, 2);
  }

  close() {
    this.dialogRef.close();
  }

  download() {
    const currentDate = new Date();
    const codebaseName = this.codebaseUrl.substring(this.codebaseUrl.lastIndexOf('/') + 1);
    this.filename = `${codebaseName}_${currentDate.toISOString()}.json`;
    const blob = new Blob([this.jsonPayload], {type: 'application/json'});
    const blobUrl = window.URL.createObjectURL(blob);
    this.sanitizedBlobUrl = this.sanitizer.bypassSecurityTrustUrl(blobUrl);

    window.URL.createObjectURL(blob)
  }

  viewInBrowser() {
    this.displayJson = true;
  }

  copyText() {
    navigator.clipboard.writeText(this.jsonPayload).then(() => {
    });
  }
}
