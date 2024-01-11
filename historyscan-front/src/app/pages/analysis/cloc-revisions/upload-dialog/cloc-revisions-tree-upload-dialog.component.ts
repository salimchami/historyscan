import {Component} from "@angular/core";
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {Router} from "@angular/router";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MaterialModule} from "../../../../shared";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";
import {CodebaseClocRevisionsFileTree} from "../codebase-cloc-revisions-file-tree.model";

@Component({
  selector: 'cloc-revisions-tree-upload-dialog',
  templateUrl: 'cloc-revisions-tree-upload-dialog.component.html',
  styleUrls: ['cloc-revisions-tree-upload-dialog.component.scss'],
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MaterialModule,
    MatCardModule,
    CommonModule
  ],
})
export class ClocRevisionsTreeUploadDialogComponent {
  fileError: string = '';
  exampleOfFilesTree: CodebaseClocRevisionsFileTree;

  constructor(
    public dialogRef: MatDialogRef<ClocRevisionsTreeUploadDialogComponent>,
  ) {
    this.exampleOfFilesTree = CodebaseClocRevisionsFileTree.of({
      "name": "root",
      "path": "/",
      "parentPath": null,
      "isFile": false,
      "currentNbLines": 0,
      "score": 1492037,
      "children": [
        {
          "name": "historyscan-back",
          "path": "historyscan-back",
          "parentPath": "/",
          "isFile": false,
          "currentNbLines": 0,
          "score": 1492037,
          "children": [{
            "name": "File.ext",
            "path": "historyscan-back/.../File.ext",
            "parentPath": "historyscan-back",
            "isFile": true,
            "currentNbLines": 23,
            "score": 150,
            "children": []
          }]
        }
      ]
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
        const json = CodebaseClocRevisionsFileTree.of(JSON.parse(e.target.result as string));
        this.dialogRef.close(json);
      };
      reader.readAsText(file);
    } else {
      this.fileError = 'Invalid file type. Please upload a JSON file.';
    }
  }
}
