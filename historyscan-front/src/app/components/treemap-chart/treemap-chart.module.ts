import {NgModule} from "@angular/core";
import {TreemapChartComponent} from "./treemap-chart.component";
import {TreemapService} from "./treemap.service";
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
  providers: [TreemapService, MatSnackBar],
  exports: [
    TreemapChartComponent,
    MatSnackBarModule
  ]
})
export class TreemapChartModule {
}
