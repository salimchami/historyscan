import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared";
import {CodebasesComponent} from "./codebases.component";
import {CodebasesService} from "./codebases.service";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  declarations: [CodebasesComponent],
  imports: [
    SharedModule,
  ],
  providers: [CodebasesService],
  exports: []
})
export class CodebasesModule {
}
