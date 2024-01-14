import {NgModule} from "@angular/core";
import {MatToolbarModule} from "@angular/material/toolbar";
import {SharedModule} from "../../shared";
import {NavbarComponent} from "./navbar.component";

@NgModule({
  declarations: [NavbarComponent],
  imports: [SharedModule,  MatToolbarModule],
  providers: [],
  exports: [NavbarComponent],
})
export class NavbarModule {
}
