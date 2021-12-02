package day02

import java.io.File

fun part1(input: List<String>) {
    var horizontalPosition = 0
    var depth = 0

    for (line in input) {
        val values: List<String> = line.split(" ")
        when(values[0]) {
            "forward" -> horizontalPosition += values[1].toInt()
            "down" -> depth += values[1].toInt()
            "up" -> depth -= values[1].toInt()
        }
    }

    println("Horizontal Position: ${horizontalPosition}, Depth: ${depth}, Result: ${horizontalPosition * depth}")
}

fun part2(input: List<String>) {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0

    for (line in input) {
        val values: List<String> = line.split(" ")
        when(values[0]) {
            "forward" -> {horizontalPosition += values[1].toInt(); depth += aim * values[1].toInt()}
            "down" -> aim += values[1].toInt()
            "up" -> aim -= values[1].toInt()
        }
    }

    println("Horizontal Position: ${horizontalPosition}, Depth: ${depth}, Result: ${horizontalPosition * depth}")
}

fun main() {
    val input: List<String> = File("src/main/kotlin/day02/day02_input.txt").readLines()

    part1(input)

    part2(input)

}