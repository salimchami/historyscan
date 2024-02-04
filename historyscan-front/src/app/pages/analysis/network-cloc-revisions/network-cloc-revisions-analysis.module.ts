import {NgModule} from "@angular/core";
import {NetworkClocRevisionsAnalysisComponent} from "./network-cloc-revisions-analysis.component";
import {NetworkChartModule} from "../../../components/network-chart/network-chart.module";
import {SharedModule} from "../../../shared";
import {TreemapChartModule} from "../../../components/treemap-chart/treemap-chart.module";

@NgModule({
  declarations: [NetworkClocRevisionsAnalysisComponent],
    imports: [SharedModule, NetworkChartModule, TreemapChartModule],
  providers: []
})
export class NetworkClocRevisionsAnalysisModule {
}
