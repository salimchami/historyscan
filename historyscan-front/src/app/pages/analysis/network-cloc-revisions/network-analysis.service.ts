import {Injectable} from "@angular/core";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {DownloadCodebaseNetwork} from "./download-codebase-network.model";
import {NetworkDownloadDialogComponent} from "./download-dialog/network-download-dialog.component";

@Injectable({providedIn: 'root'})
export class NetworkAnalysisService {

  constructor(private readonly dialog: MatDialog) {
  }

  download(nodes: DownloadCodebaseNetwork) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {nodes};
    this.dialog.open(NetworkDownloadDialogComponent, dialogConfig);
  }
}
