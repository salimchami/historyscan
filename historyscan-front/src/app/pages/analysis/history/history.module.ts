import {NgModule} from "@angular/core";
import {HistoryAnalysisComponent} from "./history-analysis.component";
import {SharedModule} from "../../../shared";

@NgModule({
  declarations: [HistoryAnalysisComponent],
  imports: [SharedModule],
  providers: []
})
export class HistoryModule {
}
