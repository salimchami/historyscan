import {NetworkChartNode} from "./network-chart-node";
import {NetworkChartLink} from "./network-chart-link";
import {NetworkChartCategory} from "./network-chart-category";

export class NetworkChartNodes {
  constructor(
    public nodes: Array<NetworkChartNode>,
    public links: Array<NetworkChartLink>,
    public categories: Array<NetworkChartCategory>,
  ) {
  }
}
