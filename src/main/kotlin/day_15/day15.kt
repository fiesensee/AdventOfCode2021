package day_15

import java.io.File
import java.util.*

data class Point(val x: Int, val y: Int, val risk: Int)

fun parseGrid(): List<Point> {
    val lines = File("src/main/kotlin/day_15/day15_input.txt").readLines()

    val grid = mutableListOf<Point>()

    for ((y, line) in lines.withIndex()) {
        val risks = line.chunked(1).map { it.toInt() }
        for ((x, risk) in risks.withIndex()) {
            grid.add(Point(x, y, risk))
        }
    }

    return grid
}

fun parseGrid2(): List<Point> {
    val lines = File("src/main/kotlin/day_15/day15_input.txt").readLines()

    val grid = mutableListOf<Point>()

    for ((y, line) in lines.withIndex()) {
        val risks = line.chunked(1).map { it.toInt() }
        for ((x, risk) in risks.withIndex()) {
            for (i in 0..4) {
                for (j in 0..4) {
                    var newRisk = risk + (i + j)
                    if (newRisk > 9) {
                        newRisk -= 9
                    }
                    grid.add(Point(x + (i * risks.size), y + (j * lines.size), newRisk))
                }
            }
        }
    }

    return grid
}

fun main() {

    val grid = parseGrid()

    aStar(grid, grid[0], grid[grid.size - 1])

    val grid2 = parseGrid2()

    aStar(grid2, grid2[0], grid2[grid2.size - 1])

}

//implement a star algorithm
//https://en.wikipedia.org/wiki/A*_search_algorithm
fun aStar(grid: List<Point>, start: Point, goal: Point) {
    val cameFrom = mutableMapOf<Point, Point>()
    val costFromStart = mutableMapOf(start to 0)

    val openSet = PriorityQueue<Point>(compareBy { costFromStart[it] })
    openSet.add(start)
    val closedSet = mutableSetOf<Point>()


    while (!openSet.isEmpty()) {
        val current = openSet.poll()
        if (current == goal) {
//            printRoute(cameFrom, current)
            println("Score: ${costFromStart[current]}")
            break
        }
        closedSet.add(current)
        for (neighbor in getNeighbors(grid, current)) {
            if (closedSet.contains(neighbor)) {
                continue
            }
            val routeFromStartScore = costFromStart[current]!! + neighbor.risk
            if (!openSet.contains(neighbor) || routeFromStartScore < costFromStart.getOrDefault(neighbor, Int.MAX_VALUE)) {
                cameFrom[neighbor] = current
                costFromStart[neighbor] = routeFromStartScore
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor)
                }
            }
        }
    }
}

fun getNeighbors(grid: List<Point>, point: Point): List<Point> {
    val neighbors = mutableListOf<Point>()
    for (x in point.x - 1..point.x + 1) {
        for (y in point.y - 1..point.y + 1) {
            if (x == point.x && y == point.y) {
                continue
            }
            if (x != point.x && y != point.y) {
                continue
            }
            grid.firstOrNull { it.x == x && it.y == y }?.let { neighbors.add(it) }
        }
    }
    return neighbors
}

fun printRoute(cameFrom: Map<Point, Point>, end: Point) {
    var current = end
    while (cameFrom.containsKey(current)) {
        current = cameFrom[current]!!
        print("$current ")
    }
    println()
}