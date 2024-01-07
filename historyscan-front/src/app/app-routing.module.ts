import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {routes} from './routes';
import {LayoutModule} from '@angular/cdk/layout';

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    LayoutModule],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
