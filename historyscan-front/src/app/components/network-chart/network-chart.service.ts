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
    const graph = this.createDataStructure(network);
    const maxScore = this.findMaxScore(network.nodes);
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

  private createDataStructure(network: NetworkNodes): NetworkChartNodes {
    let chartNodes = [];
    let chartLinks = [];
    let chartCategories = new Set<string>();
    for (let node of network.nodes) {
      let scaledSize = minSize + (node.score - minScore) * (maxSize - minSize) / (maxScore - minScore);

      chartNodes.push(new NetworkChartNode(
        node.path,
        node.name,
        node.score,
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

    // return new NetworkChartNodes(chartNodes, chartLinks, chartCategoriesArray);
    return {
      "nodes": [
        {
          "id": "0",
          "name": "Myriel",
          "symbolSize": 19.12381,
          "value": 28.685715,
          "category": 0
        },
        {
          "id": "1",
          "name": "Napoleon",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "2",
          "name": "MlleBaptistine",
          "symbolSize": 6.323809333333333,
          "value": 9.485714,
          "category": 1
        },
        {
          "id": "3",
          "name": "MmeMagloire",
          "symbolSize": 6.323809333333333,
          "value": 9.485714,
          "category": 1
        },
        {
          "id": "4",
          "name": "CountessDeLo",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "5",
          "name": "Geborand",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "6",
          "name": "Champtercier",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "7",
          "name": "Cravatte",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "8",
          "name": "Count",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "9",
          "name": "OldMan",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 0
        },
        {
          "id": "10",
          "name": "Labarre",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 1
        },
        {
          "id": "11",
          "name": "Valjean",
          "symbolSize": 66.66666666666667,
          "value": 100,
          "category": 1
        },
        {
          "id": "12",
          "name": "Marguerite",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 1
        },
        {
          "id": "13",
          "name": "MmeDeR",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 1
        },
        {
          "id": "14",
          "name": "Isabeau",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 1
        },
        {
          "id": "15",
          "name": "Gervais",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 1
        },
        {
          "id": "16",
          "name": "Tholomyes",
          "symbolSize": 17.295237333333333,
          "value": 25.942856,
          "category": 2
        },
        {
          "id": "17",
          "name": "Listolier",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 2
        },
        {
          "id": "18",
          "name": "Fameuil",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 2
        },
        {
          "id": "19",
          "name": "Blacheville",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 2
        },
        {
          "id": "20",
          "name": "Favourite",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 2
        },
        {
          "id": "21",
          "name": "Dahlia",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 2
        },
        {
          "id": "22",
          "name": "Zephine",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 2
        },
        {
          "id": "23",
          "name": "Fantine",
          "symbolSize": 28.266666666666666,
          "value": 42.4,
          "category": 2
        },
        {
          "id": "24",
          "name": "MmeThenardier",
          "symbolSize": 20.95238266666667,
          "value": 31.428574,
          "category": 7
        },
        {
          "id": "25",
          "name": "Thenardier",
          "symbolSize": 30.095235333333335,
          "value": 45.142853,
          "category": 7
        },
        {
          "id": "26",
          "name": "Cosette",
          "symbolSize": 20.95238266666667,
          "value": 31.428574,
          "category": 6
        },
        {
          "id": "27",
          "name": "Javert",
          "symbolSize": 31.923806666666668,
          "value": 47.88571,
          "category": 7
        },
        {
          "id": "28",
          "name": "Fauchelevent",
          "symbolSize": 8.152382000000001,
          "value": 12.228573,
          "category": 4
        },
        {
          "id": "29",
          "name": "Bamatabois",
          "symbolSize": 15.466666666666667,
          "value": 23.2,
          "category": 3
        },
        {
          "id": "30",
          "name": "Perpetue",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 2
        },
        {
          "id": "31",
          "name": "Simplice",
          "symbolSize": 8.152382000000001,
          "value": 12.228573,
          "category": 2
        },
        {
          "id": "32",
          "name": "Scaufflaire",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 1
        },
        {
          "id": "33",
          "name": "Woman1",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 1
        },
        {
          "id": "34",
          "name": "Judge",
          "symbolSize": 11.809524666666666,
          "value": 17.714287,
          "category": 3
        },
        {
          "id": "35",
          "name": "Champmathieu",
          "symbolSize": 11.809524666666666,
          "value": 17.714287,
          "category": 3
        },
        {
          "id": "36",
          "name": "Brevet",
          "symbolSize": 11.809524666666666,
          "value": 17.714287,
          "category": 3
        },
        {
          "id": "37",
          "name": "Chenildieu",
          "symbolSize": 11.809524666666666,
          "value": 17.714287,
          "category": 3
        },
        {
          "id": "38",
          "name": "Cochepaille",
          "symbolSize": 11.809524666666666,
          "value": 17.714287,
          "category": 3
        },
        {
          "id": "39",
          "name": "Pontmercy",
          "symbolSize": 6.323809333333333,
          "value": 9.485714,
          "category": 6
        },
        {
          "id": "40",
          "name": "Boulatruelle",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 7
        },
        {
          "id": "41",
          "name": "Eponine",
          "symbolSize": 20.95238266666667,
          "value": 31.428574,
          "category": 7
        },
        {
          "id": "42",
          "name": "Anzelma",
          "symbolSize": 6.323809333333333,
          "value": 9.485714,
          "category": 7
        },
        {
          "id": "43",
          "name": "Woman2",
          "symbolSize": 6.323809333333333,
          "value": 9.485714,
          "category": 6
        },
        {
          "id": "44",
          "name": "MotherInnocent",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 4
        },
        {
          "id": "45",
          "name": "Gribier",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 4
        },
        {
          "id": "46",
          "name": "Jondrette",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 5
        },
        {
          "id": "47",
          "name": "MmeBurgon",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 5
        },
        {
          "id": "48",
          "name": "Gavroche",
          "symbolSize": 41.06667066666667,
          "value": 61.600006,
          "category": 8
        },
        {
          "id": "49",
          "name": "Gillenormand",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 6
        },
        {
          "id": "50",
          "name": "Magnon",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 6
        },
        {
          "id": "51",
          "name": "MlleGillenormand",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 6
        },
        {
          "id": "52",
          "name": "MmePontmercy",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 6
        },
        {
          "id": "53",
          "name": "MlleVaubois",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 6
        },
        {
          "id": "54",
          "name": "LtGillenormand",
          "symbolSize": 8.152382000000001,
          "value": 12.228573,
          "category": 6
        },
        {
          "id": "55",
          "name": "Marius",
          "symbolSize": 35.58095333333333,
          "value": 53.37143,
          "category": 6
        },
        {
          "id": "56",
          "name": "BaronessT",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 6
        },
        {
          "id": "57",
          "name": "Mabeuf",
          "symbolSize": 20.95238266666667,
          "value": 31.428574,
          "category": 8
        },
        {
          "id": "58",
          "name": "Enjolras",
          "symbolSize": 28.266666666666666,
          "value": 42.4,
          "category": 8
        },
        {
          "id": "59",
          "name": "Combeferre",
          "symbolSize": 20.95238266666667,
          "value": 31.428574,
          "category": 8
        },
        {
          "id": "60",
          "name": "Prouvaire",
          "symbolSize": 17.295237333333333,
          "value": 25.942856,
          "category": 8
        },
        {
          "id": "61",
          "name": "Feuilly",
          "symbolSize": 20.95238266666667,
          "value": 31.428574,
          "category": 8
        },
        {
          "id": "62",
          "name": "Courfeyrac",
          "symbolSize": 24.609526666666667,
          "value": 36.91429,
          "category": 8
        },
        {
          "id": "63",
          "name": "Bahorel",
          "symbolSize": 22.780953333333333,
          "value": 34.17143,
          "category": 8
        },
        {
          "id": "64",
          "name": "Bossuet",
          "symbolSize": 24.609526666666667,
          "value": 36.91429,
          "category": 8
        },
        {
          "id": "65",
          "name": "Joly",
          "symbolSize": 22.780953333333333,
          "value": 34.17143,
          "category": 8
        },
        {
          "id": "66",
          "name": "Grantaire",
          "symbolSize": 19.12381,
          "value": 28.685715,
          "category": 8
        },
        {
          "id": "67",
          "name": "MotherPlutarch",
          "symbolSize": 2.6666666666666665,
          "value": 4,
          "category": 8
        },
        {
          "id": "68",
          "name": "Gueulemer",
          "symbolSize": 19.12381,
          "value": 28.685715,
          "category": 7
        },
        {
          "id": "69",
          "name": "Babet",
          "symbolSize": 19.12381,
          "value": 28.685715,
          "category": 7
        },
        {
          "id": "70",
          "name": "Claquesous",
          "symbolSize": 19.12381,
          "value": 28.685715,
          "category": 7
        },
        {
          "id": "71",
          "name": "Montparnasse",
          "symbolSize": 17.295237333333333,
          "value": 25.942856,
          "category": 7
        },
        {
          "id": "72",
          "name": "Toussaint",
          "symbolSize": 6.323809333333333,
          "value": 9.485714,
          "category": 1
        },
        {
          "id": "73",
          "name": "Child1",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 8
        },
        {
          "id": "74",
          "name": "Child2",
          "symbolSize": 4.495239333333333,
          "value": 6.742859,
          "category": 8
        },
        {
          "id": "75",
          "name": "Brujon",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 7
        },
        {
          "id": "76",
          "name": "MmeHucheloup",
          "symbolSize": 13.638097333333334,
          "value": 20.457146,
          "category": 8
        }
      ],
      "links": [
        {
          "source": "1",
          "target": "0"
        },
        {
          "source": "2",
          "target": "0"
        },
        {
          "source": "3",
          "target": "0"
        },
        {
          "source": "3",
          "target": "2"
        },
        {
          "source": "4",
          "target": "0"
        },
        {
          "source": "5",
          "target": "0"
        },
        {
          "source": "6",
          "target": "0"
        },
        {
          "source": "7",
          "target": "0"
        },
        {
          "source": "8",
          "target": "0"
        },
        {
          "source": "9",
          "target": "0"
        },
        {
          "source": "11",
          "target": "0"
        },
        {
          "source": "11",
          "target": "2"
        },
        {
          "source": "11",
          "target": "3"
        },
        {
          "source": "11",
          "target": "10"
        },
        {
          "source": "12",
          "target": "11"
        },
        {
          "source": "13",
          "target": "11"
        },
        {
          "source": "14",
          "target": "11"
        },
        {
          "source": "15",
          "target": "11"
        },
        {
          "source": "17",
          "target": "16"
        },
        {
          "source": "18",
          "target": "16"
        },
        {
          "source": "18",
          "target": "17"
        },
        {
          "source": "19",
          "target": "16"
        },
        {
          "source": "19",
          "target": "17"
        },
        {
          "source": "19",
          "target": "18"
        },
        {
          "source": "20",
          "target": "16"
        },
        {
          "source": "20",
          "target": "17"
        },
        {
          "source": "20",
          "target": "18"
        },
        {
          "source": "20",
          "target": "19"
        },
        {
          "source": "21",
          "target": "16"
        },
        {
          "source": "21",
          "target": "17"
        },
        {
          "source": "21",
          "target": "18"
        },
        {
          "source": "21",
          "target": "19"
        },
        {
          "source": "21",
          "target": "20"
        },
        {
          "source": "22",
          "target": "16"
        },
        {
          "source": "22",
          "target": "17"
        },
        {
          "source": "22",
          "target": "18"
        },
        {
          "source": "22",
          "target": "19"
        },
        {
          "source": "22",
          "target": "20"
        },
        {
          "source": "22",
          "target": "21"
        },
        {
          "source": "23",
          "target": "11"
        },
        {
          "source": "23",
          "target": "12"
        },
        {
          "source": "23",
          "target": "16"
        },
        {
          "source": "23",
          "target": "17"
        },
        {
          "source": "23",
          "target": "18"
        },
        {
          "source": "23",
          "target": "19"
        },
        {
          "source": "23",
          "target": "20"
        },
        {
          "source": "23",
          "target": "21"
        },
        {
          "source": "23",
          "target": "22"
        },
        {
          "source": "24",
          "target": "11"
        },
        {
          "source": "24",
          "target": "23"
        },
        {
          "source": "25",
          "target": "11"
        },
        {
          "source": "25",
          "target": "23"
        },
        {
          "source": "25",
          "target": "24"
        },
        {
          "source": "26",
          "target": "11"
        },
        {
          "source": "26",
          "target": "16"
        },
        {
          "source": "26",
          "target": "24"
        },
        {
          "source": "26",
          "target": "25"
        },
        {
          "source": "27",
          "target": "11"
        },
        {
          "source": "27",
          "target": "23"
        },
        {
          "source": "27",
          "target": "24"
        },
        {
          "source": "27",
          "target": "25"
        },
        {
          "source": "27",
          "target": "26"
        },
        {
          "source": "28",
          "target": "11"
        },
        {
          "source": "28",
          "target": "27"
        },
        {
          "source": "29",
          "target": "11"
        },
        {
          "source": "29",
          "target": "23"
        },
        {
          "source": "29",
          "target": "27"
        },
        {
          "source": "30",
          "target": "23"
        },
        {
          "source": "31",
          "target": "11"
        },
        {
          "source": "31",
          "target": "23"
        },
        {
          "source": "31",
          "target": "27"
        },
        {
          "source": "31",
          "target": "30"
        },
        {
          "source": "32",
          "target": "11"
        },
        {
          "source": "33",
          "target": "11"
        },
        {
          "source": "33",
          "target": "27"
        },
        {
          "source": "34",
          "target": "11"
        },
        {
          "source": "34",
          "target": "29"
        },
        {
          "source": "35",
          "target": "11"
        },
        {
          "source": "35",
          "target": "29"
        },
        {
          "source": "35",
          "target": "34"
        },
        {
          "source": "36",
          "target": "11"
        },
        {
          "source": "36",
          "target": "29"
        },
        {
          "source": "36",
          "target": "34"
        },
        {
          "source": "36",
          "target": "35"
        },
        {
          "source": "37",
          "target": "11"
        },
        {
          "source": "37",
          "target": "29"
        },
        {
          "source": "37",
          "target": "34"
        },
        {
          "source": "37",
          "target": "35"
        },
        {
          "source": "37",
          "target": "36"
        },
        {
          "source": "38",
          "target": "11"
        },
        {
          "source": "38",
          "target": "29"
        },
        {
          "source": "38",
          "target": "34"
        },
        {
          "source": "38",
          "target": "35"
        },
        {
          "source": "38",
          "target": "36"
        },
        {
          "source": "38",
          "target": "37"
        },
        {
          "source": "39",
          "target": "25"
        },
        {
          "source": "40",
          "target": "25"
        },
        {
          "source": "41",
          "target": "24"
        },
        {
          "source": "41",
          "target": "25"
        },
        {
          "source": "42",
          "target": "24"
        },
        {
          "source": "42",
          "target": "25"
        },
        {
          "source": "42",
          "target": "41"
        },
        {
          "source": "43",
          "target": "11"
        },
        {
          "source": "43",
          "target": "26"
        },
        {
          "source": "43",
          "target": "27"
        },
        {
          "source": "44",
          "target": "11"
        },
        {
          "source": "44",
          "target": "28"
        },
        {
          "source": "45",
          "target": "28"
        },
        {
          "source": "47",
          "target": "46"
        },
        {
          "source": "48",
          "target": "11"
        },
        {
          "source": "48",
          "target": "25"
        },
        {
          "source": "48",
          "target": "27"
        },
        {
          "source": "48",
          "target": "47"
        },
        {
          "source": "49",
          "target": "11"
        },
        {
          "source": "49",
          "target": "26"
        },
        {
          "source": "50",
          "target": "24"
        },
        {
          "source": "50",
          "target": "49"
        },
        {
          "source": "51",
          "target": "11"
        },
        {
          "source": "51",
          "target": "26"
        },
        {
          "source": "51",
          "target": "49"
        },
        {
          "source": "52",
          "target": "39"
        },
        {
          "source": "52",
          "target": "51"
        },
        {
          "source": "53",
          "target": "51"
        },
        {
          "source": "54",
          "target": "26"
        },
        {
          "source": "54",
          "target": "49"
        },
        {
          "source": "54",
          "target": "51"
        },
        {
          "source": "55",
          "target": "11"
        },
        {
          "source": "55",
          "target": "16"
        },
        {
          "source": "55",
          "target": "25"
        },
        {
          "source": "55",
          "target": "26"
        },
        {
          "source": "55",
          "target": "39"
        },
        {
          "source": "55",
          "target": "41"
        },
        {
          "source": "55",
          "target": "48"
        },
        {
          "source": "55",
          "target": "49"
        },
        {
          "source": "55",
          "target": "51"
        },
        {
          "source": "55",
          "target": "54"
        },
        {
          "source": "56",
          "target": "49"
        },
        {
          "source": "56",
          "target": "55"
        },
        {
          "source": "57",
          "target": "41"
        },
        {
          "source": "57",
          "target": "48"
        },
        {
          "source": "57",
          "target": "55"
        },
        {
          "source": "58",
          "target": "11"
        },
        {
          "source": "58",
          "target": "27"
        },
        {
          "source": "58",
          "target": "48"
        },
        {
          "source": "58",
          "target": "55"
        },
        {
          "source": "58",
          "target": "57"
        },
        {
          "source": "59",
          "target": "48"
        },
        {
          "source": "59",
          "target": "55"
        },
        {
          "source": "59",
          "target": "57"
        },
        {
          "source": "59",
          "target": "58"
        },
        {
          "source": "60",
          "target": "48"
        },
        {
          "source": "60",
          "target": "58"
        },
        {
          "source": "60",
          "target": "59"
        },
        {
          "source": "61",
          "target": "48"
        },
        {
          "source": "61",
          "target": "55"
        },
        {
          "source": "61",
          "target": "57"
        },
        {
          "source": "61",
          "target": "58"
        },
        {
          "source": "61",
          "target": "59"
        },
        {
          "source": "61",
          "target": "60"
        },
        {
          "source": "62",
          "target": "41"
        },
        {
          "source": "62",
          "target": "48"
        },
        {
          "source": "62",
          "target": "55"
        },
        {
          "source": "62",
          "target": "57"
        },
        {
          "source": "62",
          "target": "58"
        },
        {
          "source": "62",
          "target": "59"
        },
        {
          "source": "62",
          "target": "60"
        },
        {
          "source": "62",
          "target": "61"
        },
        {
          "source": "63",
          "target": "48"
        },
        {
          "source": "63",
          "target": "55"
        },
        {
          "source": "63",
          "target": "57"
        },
        {
          "source": "63",
          "target": "58"
        },
        {
          "source": "63",
          "target": "59"
        },
        {
          "source": "63",
          "target": "60"
        },
        {
          "source": "63",
          "target": "61"
        },
        {
          "source": "63",
          "target": "62"
        },
        {
          "source": "64",
          "target": "11"
        },
        {
          "source": "64",
          "target": "48"
        },
        {
          "source": "64",
          "target": "55"
        },
        {
          "source": "64",
          "target": "57"
        },
        {
          "source": "64",
          "target": "58"
        },
        {
          "source": "64",
          "target": "59"
        },
        {
          "source": "64",
          "target": "60"
        },
        {
          "source": "64",
          "target": "61"
        },
        {
          "source": "64",
          "target": "62"
        },
        {
          "source": "64",
          "target": "63"
        },
        {
          "source": "65",
          "target": "48"
        },
        {
          "source": "65",
          "target": "55"
        },
        {
          "source": "65",
          "target": "57"
        },
        {
          "source": "65",
          "target": "58"
        },
        {
          "source": "65",
          "target": "59"
        },
        {
          "source": "65",
          "target": "60"
        },
        {
          "source": "65",
          "target": "61"
        },
        {
          "source": "65",
          "target": "62"
        },
        {
          "source": "65",
          "target": "63"
        },
        {
          "source": "65",
          "target": "64"
        },
        {
          "source": "66",
          "target": "48"
        },
        {
          "source": "66",
          "target": "58"
        },
        {
          "source": "66",
          "target": "59"
        },
        {
          "source": "66",
          "target": "60"
        },
        {
          "source": "66",
          "target": "61"
        },
        {
          "source": "66",
          "target": "62"
        },
        {
          "source": "66",
          "target": "63"
        },
        {
          "source": "66",
          "target": "64"
        },
        {
          "source": "66",
          "target": "65"
        },
        {
          "source": "67",
          "target": "57"
        },
        {
          "source": "68",
          "target": "11"
        },
        {
          "source": "68",
          "target": "24"
        },
        {
          "source": "68",
          "target": "25"
        },
        {
          "source": "68",
          "target": "27"
        },
        {
          "source": "68",
          "target": "41"
        },
        {
          "source": "68",
          "target": "48"
        },
        {
          "source": "69",
          "target": "11"
        },
        {
          "source": "69",
          "target": "24"
        },
        {
          "source": "69",
          "target": "25"
        },
        {
          "source": "69",
          "target": "27"
        },
        {
          "source": "69",
          "target": "41"
        },
        {
          "source": "69",
          "target": "48"
        },
        {
          "source": "69",
          "target": "68"
        },
        {
          "source": "70",
          "target": "11"
        },
        {
          "source": "70",
          "target": "24"
        },
        {
          "source": "70",
          "target": "25"
        },
        {
          "source": "70",
          "target": "27"
        },
        {
          "source": "70",
          "target": "41"
        },
        {
          "source": "70",
          "target": "58"
        },
        {
          "source": "70",
          "target": "68"
        },
        {
          "source": "70",
          "target": "69"
        },
        {
          "source": "71",
          "target": "11"
        },
        {
          "source": "71",
          "target": "25"
        },
        {
          "source": "71",
          "target": "27"
        },
        {
          "source": "71",
          "target": "41"
        },
        {
          "source": "71",
          "target": "48"
        },
        {
          "source": "71",
          "target": "68"
        },
        {
          "source": "71",
          "target": "69"
        },
        {
          "source": "71",
          "target": "70"
        },
        {
          "source": "72",
          "target": "11"
        },
        {
          "source": "72",
          "target": "26"
        },
        {
          "source": "72",
          "target": "27"
        },
        {
          "source": "73",
          "target": "48"
        },
        {
          "source": "74",
          "target": "48"
        },
        {
          "source": "74",
          "target": "73"
        },
        {
          "source": "75",
          "target": "25"
        },
        {
          "source": "75",
          "target": "41"
        },
        {
          "source": "75",
          "target": "48"
        },
        {
          "source": "75",
          "target": "68"
        },
        {
          "source": "75",
          "target": "69"
        },
        {
          "source": "75",
          "target": "70"
        },
        {
          "source": "75",
          "target": "71"
        },
        {
          "source": "76",
          "target": "48"
        },
        {
          "source": "76",
          "target": "58"
        },
        {
          "source": "76",
          "target": "62"
        },
        {
          "source": "76",
          "target": "63"
        },
        {
          "source": "76",
          "target": "64"
        },
        {
          "source": "76",
          "target": "65"
        },
        {
          "source": "76",
          "target": "66"
        }
      ],
      "categories": [
        {
          "name": "A"
        },
        {
          "name": "B"
        },
        {
          "name": "C"
        },
        {
          "name": "D"
        },
        {
          "name": "E"
        },
        {
          "name": "F"
        },
        {
          "name": "G"
        },
        {
          "name": "H"
        },
        {
          "name": "I"
        }
      ]
    };
  }
}
