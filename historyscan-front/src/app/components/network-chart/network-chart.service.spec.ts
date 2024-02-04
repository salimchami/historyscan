import {NetworkChartService} from './network-chart.service';
import {NetworkNode} from "../../pages/analysis/network-cloc-revisions/network-node.model";
import {EChartsOption, SeriesOption} from "echarts";
import {NetworkNodes} from "../../pages/analysis/network-cloc-revisions/network-nodes.model";
import {NetworkLink} from "../../pages/analysis/network-cloc-revisions/network-link.model";
import {NetworkChartCategory} from "./network-chart-category";

describe('NetworkChartService', () => {
  let sut: NetworkChartService;

  beforeEach(() => {
    sut = new NetworkChartService();
  });

  it('should find the maximum score', () => {
    const nodes: Array<NetworkNode> = [
      new NetworkNode('', '',
        'src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA', 1, 15, []),
      new NetworkNode('', '', '', 1, 18, []),
      new NetworkNode('', '', '', 1, 150, []),
    ];

    const maxScore = sut['findMaxScore'](nodes);
    expect(maxScore).toEqual(150);
  });

  it('should create network options from NetworkNodes', () => {
    const network = NetworkNodes.of({
      nodes: [
        NetworkNode.of({
          name: 'network.component.ts', path: 'src/app/network/network.component.ts',
          parentPath: 'src/app/network', currentNbLines: 1, score: 1,
          links: [
            new NetworkLink('src/app/network/network.component.html', 1),
            new NetworkLink('src/app/network/network.component.scss', 1),
            new NetworkLink('src/app/network/network.service.ts', 1),
          ]
        }),
        NetworkNode.of({
          name: ' network.component.html', path: 'src/app/network/network.component.html',
          parentPath: 'src/app/network', currentNbLines: 1, score: 1,
          links: [
            new NetworkLink('src/app/network/network.component.ts', 1),
            new NetworkLink('src/app/network/network.component.scss', 1),
            new NetworkLink('src/app/network/network.service.ts', 1),
          ]
        }),
        NetworkNode.of({
          name: 'network.component.scss', path: 'src/app/network/network.component.scss',
          parentPath: 'src/app/network', currentNbLines: 1, score: 1,
          links: [
            new NetworkLink('src/app/network/network.component.html', 1),
            new NetworkLink('src/app/network/network.component.ts', 1),
            new NetworkLink('src/app/network/network.service.ts', 1),
          ]
        }),
        NetworkNode.of({
          name: 'network.service.ts', path: 'src/app/network/network.service.ts',
          parentPath: 'src/app/network', currentNbLines: 1, score: 1,
          links: [
            new NetworkLink('src/app/network/network.component.html', 1),
            new NetworkLink('src/app/network/network.component.scss', 1),
            new NetworkLink('src/app/network/network.component.ts', 1),
          ]
        }),
        NetworkNode.of({
          name: 'all.module.ts',
          path: 'src/app/tech/all.module.ts',
          parentPath: 'src/app',
          currentNbLines: 1,
          score: 1,
          links: []
        }),
        NetworkNode.of({
          name: 'en-lang.json',
          path: 'src/app/i18n/en-lang.json',
          parentPath: 'src/app/i18n',
          currentNbLines: 1,
          score: 1,
          links: [
            new NetworkLink('src/app/i18n/fr-lang.json', 1),
          ]
        }),
        NetworkNode.of({
          name: 'fr-lang.json',
          path: 'src/app/i18n/fr-lang.json',
          parentPath: 'src/app/i18n',
          currentNbLines: 1,
          score: 1,
          links: [
            new NetworkLink('src/app/i18n/en-lang.json', 1),
          ]
        }),
      ]
    });
    const options: EChartsOption = sut.toNetworkOptions(network, ['scss', 'html', 'ts', 'json'], 'title', 'description');
    const optionsSeries: SeriesOption [] = options.series as Array<SeriesOption>;
    const series = optionsSeries[0] as any;
    expect(series).toBeDefined();
    expect(series.data).toEqual([
      {
        "category": "ts",
        "id": "src/app/network/network.component.ts",
        "label": {
          "show": true
        },
        "name": "network.component.ts",
        "symbolSize": 6,
        "value": 1
      },
      {
        "category": "html",
        "id": "src/app/network/network.component.html",
        "label": {
          "show": true
        },
        "name": " network.component.html",
        "symbolSize": 6,
        "value": 1
      },
      {
        "category": "scss",
        "id": "src/app/network/network.component.scss",
        "label": {
          "show": true
        },
        "name": "network.component.scss",
        "symbolSize": 6,
        "value": 1
      },
      {
        "category": "ts",
        "id": "src/app/network/network.service.ts",
        "label": {
          "show": true
        },
        "name": "network.service.ts",
        "symbolSize": 6,
        "value": 1
      },
      {
        "category": "ts",
        "id": "src/app/tech/all.module.ts",
        "label": {
          "show": true
        },
        "name": "all.module.ts",
        "symbolSize": 6,
        "value": 1
      },
      {
        "category": "json",
        "id": "src/app/i18n/en-lang.json",
        "label": {
          "show": true
        },
        "name": "en-lang.json",
        "symbolSize": 6,
        "value": 1
      },
      {
        "category": "json",
        "id": "src/app/i18n/fr-lang.json",
        "label": {
          "show": true
        },
        "name": "fr-lang.json",
        "symbolSize": 6,
        "value": 1
      }
    ]);
    expect(series.links).toEqual([
      {
        "source": "src/app/network/network.component.ts",
        "target": "src/app/network/network.component.html"
      },
      {
        "source": "src/app/network/network.component.ts",
        "target": "src/app/network/network.component.scss"
      },
      {
        "source": "src/app/network/network.component.ts",
        "target": "src/app/network/network.service.ts"
      },
      {
        "source": "src/app/network/network.component.html",
        "target": "src/app/network/network.component.scss"
      },
      {
        "source": "src/app/network/network.component.html",
        "target": "src/app/network/network.service.ts"
      },
      {
        "source": "src/app/network/network.component.scss",
        "target": "src/app/network/network.service.ts"
      },
      {
        "source": "src/app/i18n/en-lang.json",
        "target": "src/app/i18n/fr-lang.json"
      },
    ]);
    expect(series.categories).toEqual([
      new NetworkChartCategory('scss'),
      new NetworkChartCategory('html'),
      new NetworkChartCategory('ts'),
      new NetworkChartCategory('json')
    ]);
  });
});
