import {NetworkNode} from "./network-node.model";

describe('NetworkNode', () => {
  it.each([
    [1, 6],
    [3, 7.8989],
    [15, 19.2929],
    [30, 33.5353],
    [32, 35.4343],
    [50, 52.5252],
    [72, 73.4141],
    [100, 100],
    [150, 147.4747],
  ])('should calculate symbol size - score: %d, expectedSymbolSize: %d',
    (score: number, expectedSymbolSize: number) => {
      const number = NetworkNode.of({
        name: 'network.component.ts',
        path: 'src/app/network/network.component.ts',
        parentPath: 'src/app/network',
        currentNbLines: 1,
        score: score,
        links: [
          {path: 'src/app/network/network.component.html', score: 1},
          {path: 'src/app/network/network.component.scss', score: 1},
          {path: 'src/app/network/network.service.ts', score: 1},
        ]
      }).symbolSize(1, 100);
      //       ? 6 + ((this.score - 1) * 94 / 99)
      expect(number).toBeCloseTo(expectedSymbolSize, 2);
    });
});
