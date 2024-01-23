import {Component} from "@angular/core";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MaterialModule, SharedModule} from "../../../../shared";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";
import {NetworkNodes} from "../network-nodes.model";

@Component({
  selector: 'network-upload-dialog',
  templateUrl: 'network-upload-dialog.component.html',
  styleUrls: ['network-upload-dialog.component.scss'],
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
export class NetworkUploadDialogComponent {
  fileError: string = '';
  exampleOfNetwork: NetworkNodes;

  constructor(
    public dialogRef: MatDialogRef<NetworkUploadDialogComponent>,
  ) {
    this.exampleOfNetwork = NetworkNodes.of({
      nodes: []
    });
  }

  close() {
    this.dialogRef.close();
  }

  upload(event: any) {
    const file = event?.target?.files[0];
    if (file && file.type === "application/json") {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const json = NetworkNodes.of(JSON.parse(e.target.result as string));
        this.dialogRef.close(json);
      };
      reader.readAsText(file);
    } else {
      this.fileError = 'Invalid file type. Please upload a JSON file.';
    }
  }
}
