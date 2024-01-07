import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {SharedModule} from "../../shared";

@NgModule({
  declarations: [DashboardComponent],
  imports: [SharedModule],
  providers: []
})
export class DashboardModule {
}
