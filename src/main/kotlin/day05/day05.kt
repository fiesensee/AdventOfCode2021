package day05

import java.io.File
import kotlin.math.abs

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point)

fun main() {

    val input = File("src/main/kotlin/day05/day05_input.txt").readLines()

    val lines: MutableList<Line> = mutableListOf()

    for (line in input) {
        val points = line.split(" -> ")
        val rawPoint1 = points[0].split(",").map { it.toInt() }
        val point1 = Point(rawPoint1[0], rawPoint1[1])
        val rawPoint2 = points[1].split(",").map { it.toInt() }
        val point2 = Point(rawPoint2[0], rawPoint2[1])
        if (point1.x > point2.x || point1.y > point2.y) {
            lines.add(Line(point2, point1))
        } else {
            lines.add(Line(point1, point2))
        }
    }

    val pointOccurences = mutableMapOf<Point, Int>()

    for (line in lines) {
//        comment out for part 2, uncomment for part 1
//        if (line.start.x != line.end.x && line.start.y != line.end.y) {
//            continue
//        }
        var xStep = 0
        if (line.start.x < line.end.x) {
            xStep = 1
        } else if (line.start.x > line.end.x) {
            xStep = -1
        }
        var yStep = 0
        if (line.start.y < line.end.y) {
            yStep = 1
        } else if (line.start.y > line.end.y) {
            yStep = -1
        }
        var x = line.start.x
        var y = line.start.y
        while (!(x == line.end.x && y == line.end.y)) {
            val point = Point(x, y)
            pointOccurences[point] = pointOccurences.getOrDefault(point, 0) + 1
            x += xStep
            y += yStep
        }
//        at last point after loop
        pointOccurences[Point(x, y)] = pointOccurences.getOrDefault(Point(x, y), 0) + 1
    }


    pointOccurences.entries.fold(0) { acc: Int, pointOccurrence ->
        if (pointOccurrence.value > 1) {
            acc + 1
        } else {
            acc
        }
    }.let {
        println(it)
    }
}