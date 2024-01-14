import {Routes} from '@angular/router';
import {MainComponent} from '../main';
import {inject} from "@angular/core";
import {StartupService} from "../pages/dashboard/startup/startup.service";
import {DashboardComponent} from "../pages";
import {CodebasesComponent} from "../pages/codebases/codebases.component";
import {ClocRevisionsAnalysisComponent} from "../pages/analysis/cloc-revisions/cloc-revisions-analysis.component";
import {
  NetworkClocRevisionsAnalysisComponent
} from "../pages/analysis/network-cloc-revisions/network-cloc-revisions-analysis.component";
import {HelpComponent} from "../pages/help/help.component";

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
        loadChildren: () => import('../pages/dashboard/dashboard.module')
          .then(m => m.DashboardModule)
      },
      {
        path: 'codebases',
        component: CodebasesComponent,
        loadChildren: () => import('../pages/codebases/codebases.module')
          .then(m => m.CodebasesModule)
      },
      {
        path: 'analysis/cloc-revisions',
        component: ClocRevisionsAnalysisComponent,
        loadChildren: () => import('../pages/analysis/cloc-revisions/cloc-revisions-analysis.module')
          .then(m => m.ClocRevisionsAnalysisModule)
      },
      {
        path: 'analysis/network-cloc-revisions',
        component: NetworkClocRevisionsAnalysisComponent,
        loadChildren: () => import('../pages/analysis/network-cloc-revisions/network-cloc-revisions-analysis.module')
          .then(m => m.NetworkClocRevisionsAnalysisModule)
      },
      {
        path: 'help',
        component: HelpComponent,
        loadChildren: () => import('../pages/help/help.module')
          .then(m => m.HelpModule)
      },
    ]
  }
];
