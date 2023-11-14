package com.ki960213.sheath.sorter

internal class Graph(private val nodes: Set<Node>) {

    private val dependencyMap: Map<Node, List<Node>> = nodes.associateWith { node ->
        nodes.filter { it.isDependingOn(node) }
    }

    init {
        require(nodes.all { node -> node.dependencyCount == node.dependencyCountInGraph }) {
            "모호하거나 존재하지 않는 종속 항목이 존재합니다."
        }
    }

    private val Node.dependencyCountInGraph: Int
        get() = nodes.count { node -> isDependingOn(node) }

    fun getNodesThatDependOn(node: Node): List<Node> =
        dependencyMap[node] ?: throw IllegalArgumentException("$node 노드는 그래프에 없는 노드입니다.")
}
