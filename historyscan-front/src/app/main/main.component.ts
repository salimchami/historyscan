import {Component, OnInit} from '@angular/core';
import {Observable, of} from 'rxjs';
import {LoadingService} from '../shared';
import {ActivatedRoute} from "@angular/router";
import {Startup} from "../pages/dashboard/startup/startup.model";
import {StartupService} from "../pages/dashboard/startup/startup.service";
import {LocalstorageService} from "../shared/localstorage.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {

  loading$: Observable<boolean>;
  startup: Startup = {} as Startup;

  constructor(private readonly loadingService: LoadingService,
              private readonly startupService: StartupService,
              private readonly activatedRoute: ActivatedRoute,
              private readonly localStorageService: LocalstorageService) {
    this.loading$ = of(false);
  }

  ngOnInit(): void {
    this.loading$ = this.loadingService.loading$;
    this.activatedRoute.data.subscribe(
      ({startup}) => {
        this.startup = startup;
        this.localStorageService.addCodebasesUrl(this.startup._links['list-codebases']?.href);
        this.startupService.initAddCodeBaseUrl(this.startup._links['add-codebase']?.href);
      });
  }
}
