import {NgModule} from '@angular/core';

import {HttpService, MaterialModule} from './';
import {TranslateModule} from '@ngx-translate/core';
import {RouterModule} from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatSnackBarModule} from "@angular/material/snack-bar";

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
    MatSnackBarModule
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
    MatSnackBarModule 
  ]
})
export class SharedModule {
}
