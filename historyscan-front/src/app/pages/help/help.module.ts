import {NgModule} from "@angular/core";
import {HelpComponent} from "./help.component";
import {SharedModule} from "../../shared";

@NgModule({
  declarations: [HelpComponent],
    imports: [
        SharedModule
    ],
  providers: []
})
export class HelpModule {
}
