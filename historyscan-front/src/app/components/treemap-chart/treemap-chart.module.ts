import {NgModule} from "@angular/core";
import {TreemapChartComponent} from "./treemap-chart.component";
import {TreemapService} from "./treemap.service";
import {NgxEchartsModule} from "ngx-echarts";

@NgModule({
  declarations: [
    TreemapChartComponent
  ],
  imports: [
    NgxEchartsModule,
  ],
  providers: [TreemapService],
  exports: [
    TreemapChartComponent
  ]
})
export class TreemapChartModule {
}
