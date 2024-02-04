import {NgModule} from "@angular/core";
import {TreemapChartComponent} from "./treemap-chart.component";
import {TreemapChartService} from "./treemap-chart.service";
import {NgxEchartsModule} from "ngx-echarts";
import {CommonModule} from "@angular/common";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";

@NgModule({
  declarations: [
    TreemapChartComponent
  ],
  imports: [
    NgxEchartsModule, CommonModule, MatSnackBarModule
  ],
  providers: [TreemapChartService, MatSnackBar],
  exports: [
    TreemapChartComponent,
    MatSnackBarModule
  ]
})
export class TreemapChartModule {
}
