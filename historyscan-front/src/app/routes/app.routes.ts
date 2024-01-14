import {Routes} from '@angular/router';
import {errorRoutes} from './error-routes';
import {PagesRoutes} from "./pages-routes";

export const routes: Routes = [
    ...PagesRoutes,
    ...errorRoutes,
];
