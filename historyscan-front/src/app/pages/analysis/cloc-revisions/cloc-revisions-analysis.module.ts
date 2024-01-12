import {NgModule} from "@angular/core";
import {ClocRevisionsAnalysisComponent} from "./cloc-revisions-analysis.component";
import {MaterialModule, SharedModule} from "../../../shared";
import {AnalysisService} from "./analysis.service";
import {TreemapChartModule} from "../../../components/treemap-chart/treemap-chart.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatIconModule} from "@angular/material/icon";
import {MatDialogModule} from "@angular/material/dialog";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [ClocRevisionsAnalysisComponent],
  imports: [MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    CommonModule, MaterialModule, MatIconModule, SharedModule, TreemapChartModule, ReactiveFormsModule],
  providers: [AnalysisService]
})
export class ClocRevisionsAnalysisModule {
}

