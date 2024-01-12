import {LOCALE_ID, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {LoadingService, SharedModule} from './shared';
import {MyskoolinTranslateService} from './components';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {PageNotFoundComponent} from './components/page-not-found';
import {BrowserModule} from '@angular/platform-browser';
import {MAT_SELECT_SCROLL_STRATEGY_PROVIDER} from '@angular/material/select';
import {MAT_TOOLTIP_SCROLL_STRATEGY_FACTORY_PROVIDER} from '@angular/material/tooltip';
import {MAT_AUTOCOMPLETE_SCROLL_STRATEGY} from '@angular/material/autocomplete';
import {CloseScrollStrategy, Overlay} from '@angular/cdk/overlay';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MATERIAL_SANITY_CHECKS} from '@angular/material/core';
import {HttpLoadingInterceptor} from './shared/common/interceptors/http-loading.interceptor';
import {CommonModule, registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import {UnavailableComponent} from "./components/unavailable";
import {MainComponent} from "./main";
import {RouterModule} from "@angular/router";
import {NgxEchartsModule} from "ngx-echarts";

registerLocaleData(localeFr);

export function scrollFactory(overlay: Overlay): () => CloseScrollStrategy {
  return () => overlay.scrollStrategies.close();
}

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '-lang.json');
}

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    PageNotFoundComponent,
    UnavailableComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    RouterModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts'), // or import('./path-to-my-custom-echarts')
    }),
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
  ],
  providers: [
    MyskoolinTranslateService,
    LoadingService,
    {provide: HTTP_INTERCEPTORS, useClass: HttpLoadingInterceptor, multi: true,},
    MAT_SELECT_SCROLL_STRATEGY_PROVIDER,
    MAT_TOOLTIP_SCROLL_STRATEGY_FACTORY_PROVIDER,
    {provide: MAT_AUTOCOMPLETE_SCROLL_STRATEGY, useFactory: scrollFactory, deps: [Overlay]},
    {provide: MATERIAL_SANITY_CHECKS, useValue: false},
    {provide: LOCALE_ID, useValue: 'fr-FR'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
