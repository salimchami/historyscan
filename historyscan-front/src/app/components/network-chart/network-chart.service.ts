import {Injectable} from "@angular/core";
import {EChartsOption, SeriesOption} from "echarts";
import {NetworkNodes} from "../../pages/analysis/network-cloc-revisions/network-nodes.model";
import {NetworkNode} from "../../pages/analysis/network-cloc-revisions/network-node.model";
import {NetworkChartNodes} from "./network-chart-nodes";
import {NetworkChartNode} from "./network-chart-node";
import {NetworkChartLabel} from "./network-chart-label";
import {NetworkChartLink} from "./network-chart-link";
import {NetworkChartCategory} from "./network-chart-category";

@Injectable({providedIn: 'root'})
export class NetworkChartService {
  defaultChartOptions(): EChartsOption {
    return {
      title: {
        text: 'Loading...',
        textStyle: {
          color: '#9f9f9f'
        },
      },
    };
  }

  public searchItemInNetwork(seriesItem: SeriesOption, targetItem: string) {
    if (seriesItem && 'type' in seriesItem && seriesItem.type === 'treemap' && 'data' in seriesItem) {
      const data = seriesItem.data;
      return this.findInSeries(data![0], targetItem);
    }
    return null;
  }

  toNetworkOptions(network: NetworkNodes, title: string, description: string): EChartsOption {
    const maxScore = this.findMaxScore(network.nodes);
    const minScore = this.findMinScore(network.nodes);
    const graph = this.createDataStructure(network, minScore, maxScore);
    graph.nodes.forEach(function (node) {
      node.label = {
        show: node.symbolSize > (maxScore * 70 / 100)
      };
    });
    return {
      title: {
        text: title,
        textStyle: {
          color: '#9f9f9f'
        },
        subtext: description,
        subtextStyle: {
          color: '#9f9f9f'
        }
      },
      autoResize: true,
      animation: true,
      darkMode: true,
      tooltip: {
        trigger: 'item',
        position: 'top',
        borderWidth: 2,
        borderColor: '#171d31',
        backgroundColor: '#d0d6ee',
        textStyle: {
          color: '#000',
          fontSize: 10,
          fontWeight: 'bold',
          fontFamily: 'monospace',
        },
        formatter: (params: any) => {
          const name = params.name;
          const path = params.data.path;
          const score = this.formatScore(params.value);
          return `Name: ${name}<br>Path: ${path}<br>Score: ${score}`;
        }
      },
      legend: [
        {
          data: graph.categories.map(function (a) {
            return a.name;
          })
        }
      ],
      animationDuration: 1500,
      animationEasingUpdate: 'quinticInOut',
      series: [
        {
          type: 'graph',
          layout: 'force',
          data: graph.nodes,
          links: graph.links,
          categories: graph.categories,
          force: {
            repulsion: 200,
            edgeLength: 60
          },
          roam: true,
          lineStyle: {
            color: 'source',
            curveness: 0.3
          },
          emphasis: {
            focus: 'adjacency',
            lineStyle: {
              width: 10
            }
          },
          itemStyle: {
            // color: '#1c1e2f',
            borderColor: '#fff',
          },
          label: {
            show: true,
            color: '#000',
            formatter: (params: any) => params.data.isFile ? `${params.name}\n ${params.value}` : params.name,
          },
          height: '100%'
        },

      ] as SeriesOption[],
    };
  }

  findInSeries(node: any, targetPath: string): any {
  }

  private formatScore(score: number): string {
    return score.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

  private findMaxScore(nodes: Array<NetworkNode>): number {
    return Math.max(...nodes.map(node => node.score))
  }

  private findMinScore(nodes: Array<NetworkNode>): number {
    return Math.min(...nodes.map(node => node.score))
  }

  private createDataStructure(network: NetworkNodes, minScore: any, maxScore: number): NetworkChartNodes {
    const minSize = 6;
    const maxSize = 100;
    let chartNodes = [];
    let chartLinks = [];
    let chartCategories = new Set<string>();
    for (let node of network.nodes) {
      chartNodes.push(new NetworkChartNode(
        node.path,
        node.name,
        minSize + (node.score - minScore) * (maxSize - minSize) / (maxScore - minScore),
        node.score,
        0,
        new NetworkChartLabel(false),
      ));
      for (let link of node.links) {
        chartLinks.push(new NetworkChartLink(
          node.path,
          link.path
        ));
      }
      chartCategories.add(node.parentPath);
    }
    let chartCategoriesArray =
      Array.from(chartCategories).map(name => new NetworkChartCategory(name));

    return new NetworkChartNodes(chartNodes, chartLinks, chartCategoriesArray);
  }
}
