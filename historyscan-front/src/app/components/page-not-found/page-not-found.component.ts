import {Component} from '@angular/core';
import {Location} from '@angular/common';

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
})
export class PageNotFoundComponent {

  constructor(
    private location: Location
  ) {
  }

  back(): void {
    this.location.back();
  }
}
