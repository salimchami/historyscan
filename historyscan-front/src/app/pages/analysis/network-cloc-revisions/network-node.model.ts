import {NetworkLink} from "./network-link.model";

export class NetworkNode {

  constructor(
    public name: string,
    public path: string,
    public parentPath: string,
    public currentNbLines: number,
    public score: number,
    public links: Array<NetworkLink>,
    ) {
  }

  static of(node: NetworkNode) {
    return new NetworkNode(
      node.name,
      node.path,
      node.parentPath,
      node.currentNbLines,
      node.score,
      node.links.map(link => NetworkLink.of(link)),
    );
  }
}
