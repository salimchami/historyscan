import {Component, OnDestroy} from "@angular/core";
import {TreemapService} from "./treemap.service";
import {ECharts, EChartsOption} from "echarts";
import {
  CodebaseClocRevisionsFileTree
} from "../../pages/analysis/cloc-revisions/codebase-cloc-revisions-file-tree.model";

@Component({
  selector: 'app-treemap-chart',
  templateUrl: './treemap-chart.component.html',
  styleUrls: ['./treemap-chart.component.scss'],
})
export class TreemapChartComponent implements OnDestroy {
  chartTitle: string = '';
  chartDescription: string = '';
  chartOptions: EChartsOption;
  private echartsInstance!: ECharts;

  constructor(private readonly treemapService: TreemapService) {
    this.chartOptions = this.treemapService.defaultChartOptions();
  }

  onChartInit(ec: ECharts) {
    this.echartsInstance = ec;
  }

  ngOnDestroy() {
    if (this.echartsInstance) {
      this.echartsInstance.dispose();
    }
  }

  updateChartSeries(node: CodebaseClocRevisionsFileTree, title: string, description: string) {
    this.chartTitle = title;
    this.chartDescription = description;
    this.chartOptions = this.treemapService.toTreeMapOptions(node, title, description);
  }
}
