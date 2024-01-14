import {NgModule} from '@angular/core';

import {HttpService, MaterialModule} from './';
import {TranslateModule} from '@ngx-translate/core';
import {RouterModule} from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";

@NgModule({
  providers: [
    HttpService,
  ],
  imports: [
    MaterialModule,
    TranslateModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    FlexLayoutModule,
    CommonModule,
  ],
  exports: [
    CommonModule,
    MaterialModule,
    TranslateModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    FlexLayoutModule,
  ]
})
export class SharedModule {
}
