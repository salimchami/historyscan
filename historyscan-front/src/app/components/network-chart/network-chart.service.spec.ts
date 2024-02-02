import {NetworkChartService} from './network-chart.service';
import {NetworkNode} from "../../pages/analysis/network-cloc-revisions/network-node.model";
import {EChartsOption, SeriesOption} from "echarts";
import {NetworkNodes} from "../../pages/analysis/network-cloc-revisions/network-nodes.model";

describe('NetworkChartService', () => {
  let sut: NetworkChartService;

  beforeEach(() => {
    sut = new NetworkChartService();
  });

  it('should find the maximum score', () => {
    const nodes: Array<NetworkNode> = [
      new NetworkNode('Extensions.java',
        'src/main/java/io/mycompany/theproject/domain/boundedcontexts/featureA/Extensions.java',
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
        NetworkNode.of({name: '', path: '', parentPath: '', currentNbLines: 1, score: 1, links: []})
      ]
    });
    const options: EChartsOption = sut.toNetworkOptions(network, [], 'title', 'description');
    const optionsSeries: SeriesOption [] = options.series as Array<SeriesOption>;
    const series = optionsSeries[0] as any;
    expect(series).toBeDefined();
    expect(series.data).toEqual({});
    expect(series.links).toEqual({});
    expect(series.categories).toEqual({});
  });
});
