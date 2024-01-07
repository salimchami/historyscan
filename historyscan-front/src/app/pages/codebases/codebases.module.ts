import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared";
import {CodebasesComponent} from "./codebases.component";
import {CodebasesService} from "./codebases.service";

@NgModule({
  declarations: [CodebasesComponent],
  imports: [SharedModule],
  providers: [CodebasesService],
  exports: []
})
export class CodebasesModule {
}
