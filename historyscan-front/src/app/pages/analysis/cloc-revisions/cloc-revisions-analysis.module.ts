import {NgModule} from "@angular/core";
import {ClocRevisionsAnalysisComponent} from "./cloc-revisions-analysis.component";
import {SharedModule} from "../../../shared";
import {AnalysisService} from "../../codebases/analysis.service";
import {TreemapChartModule} from "../../../components/treemap-chart/treemap-chart.module";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [ClocRevisionsAnalysisComponent],
  imports: [SharedModule, TreemapChartModule, ReactiveFormsModule],
  providers: [AnalysisService]
})
export class ClocRevisionsAnalysisModule {
}

