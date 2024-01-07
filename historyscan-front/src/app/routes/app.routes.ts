import {Routes} from '@angular/router';
import {PagesRoutes} from '../pages';
import {errorRoutes} from './error-routes';

export const routes: Routes = [
    ...PagesRoutes,
    ...errorRoutes,
];
