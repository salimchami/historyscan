import {NetworkChartLabel} from "./network-chart-label";

export class NetworkChartNode {
  constructor(
    public id: string,
    public name: string,
    public symbolSize: number,
    public value: number,
    public category: string,
    public label?: NetworkChartLabel,
  ) {
  }
}
