import { NetworkChartService } from './network-chart.service';
import { NetworkNode } from "../../pages/analysis/network-cloc-revisions/network-node.model";

describe('NetworkChartService', () => {
  let service: NetworkChartService;

  beforeEach(() => {
    service = new NetworkChartService();
  });

  it('should find the maximum score', () => {
    const nodes: Array<NetworkNode> = [
      new NetworkNode('', '', '', 1, 15, []),
      new NetworkNode('', '', '', 1, 18, []),
      new NetworkNode('', '', '', 1, 150, []),
    ];

    const maxScore = service['findMaxScore'](nodes);
    expect(maxScore).toEqual(150);
  });
});
