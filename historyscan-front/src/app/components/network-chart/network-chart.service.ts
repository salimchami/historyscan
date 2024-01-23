import {Injectable} from "@angular/core";
import {EChartsOption, SeriesOption} from "echarts";
import {NetworkNodes} from "../../pages/analysis/network-cloc-revisions/network-nodes.model";
import {NetworkNode} from "../../pages/analysis/network-cloc-revisions/network-node.model";

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
    const data = this.createDataStructure(network);
    const maxScore = this.findMaxScore(network.nodes);
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
      visualMap: {
        type: 'continuous',
        min: 0,
        max: maxScore,
        inRange: {
          color: [
            '#2f6e7c',
            '#8b63d3',
            '#a1597f',

            '#c95239',
            '#c74a21',
            '#b13333',
          ]
        },
      },
      series: [
        {
          type: 'treemap',
          itemStyle: {
            color: '#1c1e2f',
            borderColor: '#fff',
          },
          label: {
            show: true,
            color: '#000',
            formatter: (params: any) => params.data.isFile ? `${params.name}\n ${params.value}` : params.name,
          },
          breadcrumb: {
            top: 1,
          },
          height: '100%',
          upperLabel: {
            color: '#bdb8b8',
            backgroundColor: '#1c1e2f',
            show: true,
            height: 20,
            fontSize: 10,
            lineHeight: 10,
          },
          visibleMin: 2,
          levels: this.levelOption(),
          data,
          selectedMode: 'single',
        },

      ] as SeriesOption[],
    };
  }

  private findMaxScore(nodes: Array<NetworkNode>): number {
    return 0;
  }

  private levelOption() {
    return [
      {
        emphasis: {
          itemStyle: {
            borderColor: '#ddd'
          }
        },
        upperLabel: {
          show: true
        },
        borderWidth: 3,
        borderColorSaturation: 0.6,
        mappingBy: 'value',
      }
    ];
  }

  private formatScore(score: number): string {
    return score.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  }

  private createDataStructure(network: NetworkNodes): any[] {
    return [];
  }

  findInSeries(node: any, targetPath: string): any {
  }
}
