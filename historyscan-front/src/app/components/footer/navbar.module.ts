import {NgModule} from "@angular/core";
import {SharedModule} from "../../shared";
import {FooterComponent} from "./footer.component";

@NgModule({
  declarations: [FooterComponent],
  imports: [SharedModule],
  providers: [],
  exports: [FooterComponent],
})
export class FooterModule {
}
