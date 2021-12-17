package day_12

import java.io.File

data class Node (val id: String, val isBig: Boolean, val neighbours: MutableList<String>)

fun getNodes(): List<Node> {
    val nodes = mutableListOf<Node>()

    val lines = File("src/main/kotlin/day_12/day12_input.txt").readLines()
    for (line in lines) {
        val parts = line.split("-")
        val firstCave = parts[0]
        val secondCave = parts[1]

        var firstCaveNode = nodes.find { it.id == firstCave }
        if (firstCaveNode == null) {
            firstCaveNode = Node(firstCave, Character.isUpperCase(firstCave[0]), mutableListOf())
            nodes.add(firstCaveNode)
        }
        var secondCaveNode = nodes.find { it.id == secondCave }
        if (secondCaveNode == null) {
            secondCaveNode = Node(secondCave, Character.isUpperCase(secondCave[0]), mutableListOf())
            nodes.add(secondCaveNode)
        }

        firstCaveNode.neighbours.add(secondCave)
        secondCaveNode.neighbours.add(firstCave)

    }

    return nodes
}

fun main() {

    val nodes = getNodes()

    println(nodes)

    val endNode = nodes.find { it.id == "end" }

    val routes = walkGraph(nodes, endNode!!, mutableListOf())

    println("Routes: $routes")

    val routes2 = walkGraph2(nodes, endNode!!, mutableListOf(), "")

    println("Routes2: $routes2")

}

fun walkGraph(nodes: List<Node>, node: Node, visited: MutableList<String>): Int {
    if (node.id == "start") {
        println("Found route from start to $node")
        return 1
    }
    visited.add(node.id)
    println("Visited $visited at ${node.id}")
    var routes = 0
    for (neighbour in node.neighbours) {
        val neighbourNode = nodes.find { it.id == neighbour }
        if (!visited.contains(neighbour) || neighbourNode!!.isBig) {
            routes += walkGraph(nodes, neighbourNode!!, visited.toMutableList())
        }
    }

    return routes
}

fun walkGraph2(nodes: List<Node>, node: Node, visited: MutableList<String>, smallCaveExemption: String): Int {
    if (node.id == "start") {
        println("Found route from start to $node")
        return 1
    }
    visited.add(node.id)
    println("Visited $visited at ${node.id} with exemption $smallCaveExemption")
    var routes = 0
    for (neighbour in node.neighbours) {
        val neighbourNode = nodes.find { it.id == neighbour }
        if (!visited.contains(neighbour) || neighbourNode!!.isBig) {
            routes += walkGraph2(nodes, neighbourNode!!, visited.toMutableList(), smallCaveExemption)
        } else if (visited.contains(neighbour) && !neighbourNode.isBig && neighbour != "start" && neighbour != "end") {
            if (smallCaveExemption.isEmpty()) {
                routes += walkGraph2(nodes, neighbourNode, visited.toMutableList(), neighbour)
            } else if (smallCaveExemption == neighbour && visited.count { it == neighbour } < 2) {
                routes += walkGraph2(nodes, neighbourNode, visited.toMutableList(), smallCaveExemption)
            }
        }
    }

    return routes
}