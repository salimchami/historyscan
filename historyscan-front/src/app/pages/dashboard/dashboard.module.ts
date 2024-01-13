import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";
import {SharedModule} from "../../shared";
import {MatCardModule} from "@angular/material/card";

@NgModule({
  declarations: [DashboardComponent],
    imports: [SharedModule, MatCardModule],
  providers: []
})
export class DashboardModule {
}
