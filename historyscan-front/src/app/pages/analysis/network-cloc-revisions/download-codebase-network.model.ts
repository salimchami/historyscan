import {NetworkNodes} from "./network-nodes.model";

export class DownloadCodebaseNetwork {
  constructor(
    public codebaseUrl: string,
    public codebaseBranch: string,
    public payload: NetworkNodes) {

  }

}
