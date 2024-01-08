import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {Component} from "@angular/core";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";

@Component({
  selector: 'root-folder-dialog',
  styleUrls: ['root-folder-dialog.scss'],
  templateUrl: 'root-folder-dialog.html',
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
  ],
})
export class RootFolderDialog {

  folder: string = '/';

  constructor(
    public dialogRef: MatDialogRef<RootFolderDialog>,
    // @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submitFolder() {
    this.dialogRef.close(this.folder);
  }

  folderValid() {
    return this.folder && this.folder.length >= 1;
  }
}
