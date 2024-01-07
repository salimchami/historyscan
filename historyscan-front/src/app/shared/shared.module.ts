import {NgModule} from '@angular/core';

import {HttpService, MaterialModule} from './';
import {NavbarComponent} from '../components';
import {TranslateModule} from '@ngx-translate/core';
import {RouterModule} from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {FooterComponent} from "../components/footer";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FlexLayoutModule} from "@angular/flex-layout";

@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
  ],
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
    NavbarComponent,
    FooterComponent,
  ]
})
export class SharedModule {
}
