import {Injectable} from "@angular/core";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslateService} from "@ngx-translate/core";

@Injectable({providedIn: 'root'})
export class NotificationService {
  constructor(private readonly snackBar: MatSnackBar,
              private readonly translateService: TranslateService) {
  }

  private showNotification(message: string) {
    this.snackBar.open(message, this.translateService.instant('global.buttons.close'),
      {
        duration: 5000,
        horizontalPosition: 'center',
        verticalPosition: 'top',
        panelClass: ['snack-bar'],
      });
  }

  showCopiedText(path: string) {
    this.translateService.get('global.notifications.copied-path').subscribe((message: string) => {
      this.showNotification(`${message}: ${path}`);
    });
  }
}
