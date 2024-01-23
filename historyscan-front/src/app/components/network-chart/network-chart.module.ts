import {NgModule} from "@angular/core";
import {NetworkChartComponent} from "./network-chart.component";
import {NgxEchartsDirective} from "ngx-echarts";

@NgModule({
    declarations: [
        NetworkChartComponent
    ],
    imports: [
        NgxEchartsDirective
    ],
    exports: [
        NetworkChartComponent
    ]
})
export class NetworkChartModule {
}
