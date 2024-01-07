import {NgModule} from "@angular/core";
import {NetworkClocRevisionsAnalysisComponent} from "./network-cloc-revisions-analysis.component";
import {NetworkChartModule} from "../../../components/network-chart/network-chart.module";
import {SharedModule} from "../../../shared";

@NgModule({
  declarations: [NetworkClocRevisionsAnalysisComponent],
  imports: [SharedModule, NetworkChartModule],
  providers: []
})
export class NetworkClocRevisionsAnalysisModule {
}
