import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-unavailable',
  styleUrls: ['./unavailable.component.scss'],
  templateUrl: './unavailable.component.html',
})
export class UnavailableComponent {

  constructor(
    private readonly router: Router,
  ) {
  }

  root(): void {
    this.router.navigate(['/']).then();
  }
}
