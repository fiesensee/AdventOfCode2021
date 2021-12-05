package day05

import java.io.File
import kotlin.math.abs

data class Point(val x: Int, val y: Int)
data class Line(val start: Point, val end: Point)


fun main() {
    val test = 9..2
    val input = File("src/main/kotlin/day05/day05_test_input.txt").readLines()

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
//        lines.add(Line(point1, point2))
    }

    println(lines)

    val overlappingPoints: MutableSet<Point> = mutableSetOf()

    for (line in lines) {
        if (line.start.x != line.end.x && line.start.y != line.end.y) {
            continue
        }
        for (otherLine in lines) {
            if (otherLine.start.x != otherLine.end.x && otherLine.start.y != otherLine.end.y) {
                continue
            }
            if (line != otherLine) {
                if (line.start.x == otherLine.start.x && line.end.x == otherLine.end.x) {

                    val points = abs(line.end.y - otherLine.end.y)
                    println("$points")
                } else if (line.start.y == otherLine.start.y && line.end.y == otherLine.end.y) {
                    val points = abs(line.end.x - otherLine.end.x)
                    println("$points")
                }
//                should intersect at 7,0 7,4 -> 9,4 3,4
                getIntersectionPoint(line, otherLine)?.let {
                    println("Intersection point: $it")
                }
            }
        }
    }
}

fun getIntersectionPoint(line1: Line, line2: Line): Point? {
    val x1 = line1.start.x
    val y1 = line1.start.y
    val x2 = line1.end.x
    val y2 = line1.end.y
    val x3 = line2.start.x
    val y3 = line2.start.y
    val x4 = line2.end.x
    val y4 = line2.end.y

    val denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)
    if (denominator == 0) {
        return null
    }

    val x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator
    val y = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator

//    println(x in x1..x2)
//    println(x in x3..x4)
//    println(y in y1..y2)
//    println(y in y3..y4)
    if (x in x1..x2 && x in x3..x4 && y in y1..y2 && y in y3..y4) {
        return Point(x, y)
    }

    return null
}