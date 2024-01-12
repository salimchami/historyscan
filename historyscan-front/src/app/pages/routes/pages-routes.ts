import {Routes} from '@angular/router';
import {MainComponent} from '../../main';
import {inject} from "@angular/core";
import {StartupService} from "../dashboard/startup/startup.service";
import {DashboardComponent} from "../dashboard";
import {CodebasesComponent} from "../codebases/codebases.component";
import {ClocRevisionsAnalysisComponent} from "../analysis/cloc-revisions/cloc-revisions-analysis.component";
import {
  NetworkClocRevisionsAnalysisComponent
} from "../analysis/network-cloc-revisions/network-cloc-revisions-analysis.component";

export const PagesRoutes: Routes = [
  {
    path: '',
    component: MainComponent,
    resolve: {startup: () => inject(StartupService).startup()},
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
        loadChildren: () => import('../dashboard/dashboard.module')
          .then(m => m.DashboardModule)
      },
      {
        path: 'codebases',
        component: CodebasesComponent,
        loadChildren: () => import('../codebases/codebases.module')
          .then(m => m.CodebasesModule)
      },
      {
        path: 'analysis/cloc-revisions',
        component: ClocRevisionsAnalysisComponent,
        loadChildren: () => import('../analysis/cloc-revisions/cloc-revisions-analysis.module')
          .then(m => m.ClocRevisionsAnalysisModule)
      },
      {
        path: 'analysis/network-cloc-revisions',
        component: NetworkClocRevisionsAnalysisComponent,
        loadChildren: () => import('../analysis/network-cloc-revisions/network-cloc-revisions-analysis.module')
          .then(m => m.NetworkClocRevisionsAnalysisModule)
      },
    ]
  }
];
