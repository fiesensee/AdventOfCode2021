package day_11

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun getGrid(): List<List<Int>> {
    val lines = File("src/main/kotlin/day_11/day11_input.txt").readLines()

    val grid = mutableListOf<List<Int>>()

    for (line in lines) {
        val row = line.chunked(1).map { it.toInt() }
        grid.add(row)
    }

    return grid
}

fun part1(grid: List<List<Int>>) {
    var flashes = 0

    var oldGrid = grid.toList()
    for (i in 1..100) {
        val newGrid = mutableListOf<MutableList<Int>>()

//        first increase every octopus by one
        for (row in oldGrid) {
            val newRow = row.map { it + 1 }.toMutableList()
            newGrid.add(newRow)
        }

//        then check if any octopus is bigger than 9, increase neighbours, continue till no octopus is bigger than 9
        while (true) {
            var changed = false
            for ((y, row) in newGrid.withIndex()) {
                for ((x, octopus) in row.withIndex()) {
                    if (octopus > 9) {
                        flashes++
                        changed = true
                        val neighbours = getNeighbours(newGrid, y, x)
                        neighbours.forEach {
                            newGrid[it.first][it.second] += 1
                        }
                        newGrid[y][x] = 0
                    }
                }
            }
            if (!changed) {
                break
            }
        }

        oldGrid = newGrid.toList()
    }

    println("Flashes (Part 1): $flashes")
}

fun part2(grid: List<List<Int>>) {

    var iterations = 1

    var oldGrid = grid.toList()
    while(true) {
        var flashesPerIteration = 0
        val newGrid = mutableListOf<MutableList<Int>>()

//        first increase every octopus by one
        for (row in oldGrid) {
            val newRow = row.map { it + 1 }.toMutableList()
            newGrid.add(newRow)
        }

//        then check if any octopus is bigger than 9, increase neighbours, continue till no octopus is bigger than 9
        while (true) {
            var changed = false
            for ((y, row) in newGrid.withIndex()) {
                for ((x, octopus) in row.withIndex()) {
                    if (octopus > 9) {
                        flashesPerIteration++
                        changed = true
                        val neighbours = getNeighbours(newGrid, y, x)
                        neighbours.forEach {
                            newGrid[it.first][it.second] += 1
                        }
                        newGrid[y][x] = 0
                    }
                }
            }
            if (!changed) {
                break
            }
        }

        oldGrid = newGrid.toList()

        if (flashesPerIteration == oldGrid.size * oldGrid[0].size) {
            break
        }
        iterations++
    }

    println("Iterations (Part 2): $iterations")
}

fun main() {
    val grid = getGrid()

    part1(grid)

    part2(grid)

}

fun getNeighbours(grid: List<List<Int>>, y: Int, x: Int): List<Pair<Int, Int>> {
    val neighbours = mutableListOf<Pair<Int, Int>>()

    var row = max(0, y - 1)
    var col = max(0, x - 1)

    while (row <= min(grid.size - 1, y + 1)) {
        while (col <= min(grid[row].size - 1, x + 1)) {
            if ((row != y || col != x) && grid[row][col] != 0) {
                neighbours.add(Pair(row, col))
            }
            col++
        }
        row++
        col = max(0, x - 1)
    }

    return neighbours
}

fun getNeighbouringOctopusValues(grid: List<List<Int>>, y: Int, x: Int): Int {
    var sum = 0

    var row = max(0, y - 1)
    var col = max(0, x - 1)

    while (row <= min(grid.size - 1, y + 1)) {
        while (col <= min(grid[row].size - 1, x + 1)) {
            if ((row != y || col != x) && grid[row][col] >= 8) {
                sum += grid[row][col]
            }
            col++
        }
        row++
        col = max(0, x - 1)
    }

    return sum
}