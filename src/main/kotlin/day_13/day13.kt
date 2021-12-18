package day_13

import java.io.File

val fiRegex = "fold along (\\w)=(\\d+)".toRegex()

data class Point(var x: Int, var y: Int)
data class FoldingInstruction(val axis: Char, val distance: Int)

fun parseInput(): Pair<Set<Point>, List<FoldingInstruction>> {
    val lines = File("src/main/kotlin/day_13/day13_input.txt").readLines()

    val points = mutableSetOf<Point>()

    var foldingInstructionStart = 0

    for ((index, line) in lines.withIndex()) {
        if (line.isEmpty()) {
            foldingInstructionStart = index
            break
        }
        val values = line.split(",").map { it.toInt() }
        points.add(Point(values[0], values[1]))
    }

    val foldingInstructions = mutableListOf<FoldingInstruction>()
    val foldingInstructionLines = lines.subList(foldingInstructionStart, lines.size)

    for (line in foldingInstructionLines) {
        val matchResult = fiRegex.matchEntire(line)
        if (matchResult != null) {
            val axis = matchResult.groupValues[1][0]
            val distance = matchResult.groupValues[2].toInt()
            foldingInstructions.add(FoldingInstruction(axis, distance))
        }
    }

    return Pair(points, foldingInstructions)
}

fun main() {

    var (points, foldingInstructions) = parseInput()

    val maxX = points.maxByOrNull { it.x }!!.x
    val maxY = points.maxByOrNull { it.y }!!.y

    val grid = Array(maxY + 1) { Array(maxX + 1) { "." } }

    for (point in points) {
        grid[point.y][point.x] = "#"
    }

    for (foldingInstruction in foldingInstructions) {
        val newPoints = mutableSetOf<Point>()
        for(point in points) {
            if (foldingInstruction.axis == 'x') {
                if (point.x > foldingInstruction.distance) {
                    grid[point.y][point.x] = "."
                    val newX = foldingInstruction.distance - (point.x - foldingInstruction.distance)
                    grid[point.y][newX] = "#"
                    newPoints.add(Point(newX, point.y))
                } else {
                    newPoints.add(Point(point.x, point.y))
                }
            } else if (foldingInstruction.axis == 'y') {
                if (point.y > foldingInstruction.distance) {
                    grid[point.y][point.x] = "."
                    val newY = foldingInstruction.distance - (point.y - foldingInstruction.distance)
                    grid[newY][point.x] = "#"
                    newPoints.add(Point(point.x, newY))
                } else {
                    newPoints.add(Point(point.x, point.y))
                }
            }
        }
        points = newPoints

    }

    val resultFile = File("src/main/kotlin/day_13/day13_result.txt")
    val resultString = StringBuilder()
    for (row in grid) {
        resultString.append(row.joinToString("") + "\n")
    }
    resultFile.writeText(resultString.toString())

    print("Part 1: ${points.size}")



}