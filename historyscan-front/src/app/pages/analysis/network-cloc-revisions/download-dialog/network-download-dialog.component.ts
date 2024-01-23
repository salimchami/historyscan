import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MaterialModule, SharedModule} from "../../../../shared";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";
import {NetworkNodes} from "../network-nodes.model";

@Component({
  selector: 'network-download-dialog',
  templateUrl: 'network-download-dialog.component.html',
  styleUrls: ['network-download-dialog.component.scss'],
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
export class NetworkDownloadDialogComponent {

  codebaseUrl: any;
  codebaseBranch: any;
  jsonPayload: string;
  filename: string = '';
  displayJson: boolean = false;
  sanitizedBlobUrl!: SafeUrl;
  private readonly payload: NetworkNodes;

  constructor(
    public dialogRef: MatDialogRef<NetworkDownloadDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private sanitizer: DomSanitizer
  ) {
    this.payload = data.nodes.payload;
    this.codebaseUrl = data.nodes.codebaseUrl;
    this.codebaseBranch = data.nodes.codebaseBranch;
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
