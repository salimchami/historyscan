import {NetworkNode} from "./network-node.model";

export class NetworkNodes {

  constructor(public nodes: Array<NetworkNode>) {
  }

  static of(network: NetworkNodes) {
    return new NetworkNodes(network.nodes.map(node => NetworkNode.of(node)));
  }

  static copyWithExtensions(network: NetworkNodes, extensionsToKeep: Array<string>): NetworkNodes {
    //TODO : implement this
    return new NetworkNodes([]);
  }
}
