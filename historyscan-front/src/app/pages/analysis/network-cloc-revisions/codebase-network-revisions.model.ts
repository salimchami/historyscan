import {CodebaseClocRevisionsIgnoredFile} from "../cloc-revisions/codebase-cloc-revisions-ignored-file.model";
import {NetworkNodes} from "./network-nodes.model";

export class CodebaseNetworkRevisions {

  constructor(public network: NetworkNodes,
              public ignoredRevisions: Array<CodebaseClocRevisionsIgnoredFile>,
              public extensions: Array<string>) {
  }

  static of(networkRevisions: CodebaseNetworkRevisions): CodebaseNetworkRevisions {
    return new CodebaseNetworkRevisions(
      NetworkNodes.of(networkRevisions.network),
      networkRevisions.ignoredRevisions.map(rev =>
        CodebaseClocRevisionsIgnoredFile.of(rev)),
      networkRevisions.extensions
    );
  }

  isEmpty() {
    return !this.network.nodes?.length;
  }

  copy(extensionsToKeep: Array<string>) {
    return new CodebaseNetworkRevisions(
      NetworkNodes.copyWithExtensions(this.network, extensionsToKeep),
      this.ignoredRevisions.map((ignoredFile) => CodebaseClocRevisionsIgnoredFile.of(ignoredFile)),
      this.extensions.map((extension) => extension)
    );

  }

  static empty() {
    return new CodebaseNetworkRevisions(new NetworkNodes([]), [], [])
  }
}
