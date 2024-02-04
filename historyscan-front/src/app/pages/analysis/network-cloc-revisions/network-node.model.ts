import {NetworkLink} from "./network-link.model";

export class NetworkNode {
  minSize = 6;
  maxSize = 100;

  constructor(
    public name: string,
    public path: string,
    public parentPath: string,
    public currentNbLines: number,
    public score: number,
    public links: Array<NetworkLink>,
  ) {
  }

  static of(node: any) {
    return new NetworkNode(
      node.name,
      node.path,
      node.parentPath,
      node.currentNbLines,
      node.score,
      node.links.map((link: any) => NetworkLink.of(link)),
    );
  }

  extension(): string {
    const pointIndex = this.path.lastIndexOf(".");
    if (pointIndex !== -1) {
      return this.path.substring(pointIndex + 1);
    }
    const slashIndex = this.path.lastIndexOf("/");
    return this.path.substring(slashIndex + 1);
  }

  symbolSize(minScore: number, maxScore: number) {
    if (minScore === maxScore) {
      return this.minSize;
    }
    return this.score !== undefined
      ? this.minSize + (((this.score - minScore) * (this.maxSize - this.minSize)) / (maxScore - minScore))
      : this.minSize
  }
}
