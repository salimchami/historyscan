package io.sch.historyscan.domain.contexts.analysis.network

class NetworkNode(
    val name: String,
    val path: String,
    val parentPath: String,
    val currentNbLines: Long,
    val score: Long,
    val links: List<NetworkLink>
) : Comparable<NetworkNode> {
    override fun compareTo(other: NetworkNode): Int {
        return Comparator
            .comparing(NetworkNode::score, Comparator.reverseOrder())
            .thenComparing(NetworkNode::name)
            .compare(this, other)
    }
}
