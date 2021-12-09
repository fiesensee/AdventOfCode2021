package day_09

import java.io.File

fun readInput(path: String): List<List<Int>> {
    val result = mutableListOf<List<Int>>()

    val lines = File(path).readLines()
    for (line in lines) {
        val row = line.chunked(1).map { it.toInt() }
        result.add(row)
    }

    return result
}

fun main() {
    val heightmap: List<List<Int>> = readInput("src/main/kotlin/day_09/day09_input.txt")

    for (line in heightmap) {
        println(line)
    }

    var result = 0
    val lowPoints = mutableListOf<Pair<Int, Int>>()

    for ((y, line) in heightmap.withIndex()) {
        for ((x, cell) in line.withIndex()) {
            val neighbours = getNeighbours(heightmap, x, y)
            if (neighbours.none { it <= cell }) {
                result += cell + 1
                lowPoints.add(Pair(x, y))
            }
        }
    }

    println("Part1: $result")

    val basinSizes = mutableListOf<Int>()

    for (point in lowPoints) {
        val basinSize = getBasinSize(heightmap, point.first, point.second)
        basinSizes.add(basinSize)
    }

    basinSizes.sortDescending()
    println("Part2: ${basinSizes.subList(0, 3)}")
    println("Part 2: ${basinSizes.subList(0, 3).fold(1) { acc, i -> acc * i }}")
}

fun getBasinSize(heightmap: List<List<Int>>, x: Int, y: Int): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue = mutableListOf<Pair<Int, Int>>()
    queue.add(Pair(x, y))

    while (queue.isNotEmpty()) {
        val point = queue.removeAt(0)
        visited.add(point)

        val neighbours = getNeighboursWithPos(heightmap, point.first, point.second)
        for (neighbour in neighbours) {
            if (neighbour.first < 9 && !visited.contains(neighbour.second)) {
                queue.add(neighbour.second)
            }
        }
    }

    return visited.size
}

fun getNeighboursWithPos(heightMap: List<List<Int>>, x: Int, y: Int): List<Pair<Int, Pair<Int, Int>>> {
    val result = mutableListOf<Pair<Int, Pair<Int, Int>>>()
    //    left
    if (x > 0) {
        val point = Pair(heightMap[y][x - 1], Pair(x - 1, y))
        result.add(point)
    }
//    right
    if (x < heightMap[y].size - 1) {
        val point = Pair(heightMap[y][x + 1], Pair(x + 1, y))
        result.add(point)
    }

//    top
    if (y > 0) {
        val point = Pair(heightMap[y - 1][x], Pair(x, y - 1))
        result.add(point)
    }

//    bottom
    if (y < heightMap.size - 1) {
        val point = Pair(heightMap[y + 1][x], Pair(x, y + 1))
        result.add(point)
    }

    return result
}

fun getNeighbours(heightMap: List<List<Int>>, x: Int, y: Int): List<Int> {
    val result = mutableListOf<Int>()

//    left
    if (x > 0) {
        result.add(heightMap[y][x - 1])
    }
//    right
    if (x < heightMap[y].size - 1) {
        result.add(heightMap[y][x + 1])
    }

//    top
    if (y > 0) {
        result.add(heightMap[y - 1][x])
    }

//    bottom
    if (y < heightMap.size - 1) {
        result.add(heightMap[y + 1][x])
    }

    return result
}