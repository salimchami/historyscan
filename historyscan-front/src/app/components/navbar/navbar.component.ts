import {Component} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  styleUrls: ['./navbar.component.scss'],
  templateUrl: 'navbar.component.html',
})
export class NavbarComponent {
  constructor(private readonly router: Router) {
  }
}
