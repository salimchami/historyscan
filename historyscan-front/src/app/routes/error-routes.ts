import {Routes} from '@angular/router';
import {PageNotFoundComponent} from '../components/page-not-found';
import {UnavailableComponent} from "../components/unavailable";

export const errorRoutes: Routes = [
  {
    path: 'not-found',
    component: PageNotFoundComponent,
  },
  {
    path: 'unavailable',
    component: UnavailableComponent,
  },
  {
    path: '**',
    redirectTo: '/not-found'
  }
];
