import {Component, OnDestroy} from "@angular/core";
import {NetworkNodes} from "../../pages/analysis/network-cloc-revisions/network-nodes.model";
import {ECharts, EChartsOption} from "echarts";
import {NetworkChartService} from "./network-chart.service";
import {NotificationService} from "../../shared/common/notification.service";

@Component({
  selector: 'app-network-chart',
  templateUrl: './network-chart.component.html',
  styleUrls: ['./network-chart.component.scss'],
})
export class NetworkChartComponent implements OnDestroy {
  chartTitle: string = '';
  chartDescription: string = '';
  chartOptions: EChartsOption;
  private echartsInstance!: ECharts;

  constructor(private readonly networkService: NetworkChartService,
              private readonly notificationService: NotificationService) {
    this.chartOptions = this.networkService.defaultChartOptions();
  }

  onChartInit(ec: ECharts) {
    this.echartsInstance = ec;
    this.echartsInstance.resize({height: 700})
    this.copyPathOnNodeClick();
  }

  ngOnDestroy() {
    if (this.echartsInstance) {
      this.echartsInstance.dispose();
    }
  }

  updateChartSeries(network: NetworkNodes, extensions: Array<string>, title: string, description: string) {
    this.chartTitle = title;
    this.chartDescription = description;
    this.chartOptions = this.networkService.toNetworkOptions(network, extensions, title, description);
  }

  tooltipOn(targetItem: string) {
    if (this.chartOptions.series && Array.isArray(this.chartOptions.series)) {
      const seriesItem = this.chartOptions.series[0];
      const foundItem = this.networkService.searchItemInNetwork(seriesItem, targetItem);
      if (foundItem?.dataIndex) {
        this.echartsInstance.dispatchAction({
          type: 'showTip',
          seriesIndex: 0,
          dataIndex: foundItem.dataIndex
        });
      }
    }
  }

  copyPathOnNodeClick() {
    this.echartsInstance.on('click', (params: any) => {
      if (params?.data?.id) {
        navigator.clipboard.writeText(params.data.id).then(() => {
          this.notificationService.showCopiedText(params.data.id);
        });
      }
    });
  }
}
