import {NgModule} from "@angular/core";
import {TreemapChartComponent} from "./treemap-chart.component";
import {TreemapService} from "./treemap.service";
import {NgxEchartsModule} from "ngx-echarts";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    TreemapChartComponent
  ],
  imports: [
    NgxEchartsModule, CommonModule
  ],
  providers: [TreemapService],
  exports: [
    TreemapChartComponent
  ]
})
export class TreemapChartModule {
}
