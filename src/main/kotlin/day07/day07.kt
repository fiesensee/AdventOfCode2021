package day07

import java.io.File

fun main() {
    val numbers = File("src/main/kotlin/day07/day07_input.txt").readLines()[0].split(",").map { it.toInt() }

    val median = numbers.sorted()[numbers.size / 2]

    var fuelUsed = 0

    for (number in numbers) {
        fuelUsed += kotlin.math.abs(number - median)
    }

    println("Part 1: $fuelUsed")

    var currentFuelUsage = calculateTotalFuelUsage(numbers, 1)
    var lastFuelUsage = calculateTotalFuelUsage(numbers, 0)
    var currentTarget = 2

    while (currentFuelUsage < lastFuelUsage) {
        val totalFuelUsage = calculateTotalFuelUsage(numbers, currentTarget)
        lastFuelUsage = currentFuelUsage
        currentFuelUsage = totalFuelUsage
        currentTarget++
    }

    println("Part 2: $lastFuelUsage")

}

fun calculateTotalFuelUsage(numbers: List<Int>, targetPosition: Int): Int {
    var totalFuelUsage = 0
    for (number in numbers) {
        totalFuelUsage += calculateFuelUsage(number, targetPosition)
    }
    return totalFuelUsage
}

fun calculateFuelUsage(position: Int, targetPosition: Int): Int {
    var fuelUsed = 0
    var finalPosition = targetPosition
    var currentPosition = position
    if (position > targetPosition) {
        currentPosition = targetPosition
        finalPosition = position
    }
    var currentFuelExtra = 1
    while (currentPosition != finalPosition) {
        fuelUsed += currentFuelExtra
        currentFuelExtra += 1
        currentPosition += 1
    }

    return fuelUsed
}