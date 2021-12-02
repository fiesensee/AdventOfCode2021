package day1

import java.io.File

fun part1(numbers: List<Int>) {

    var counter = 0

    var previousNumber = numbers.first()


    for (number in numbers) {
        if (number > previousNumber) {
            counter++
        }
        previousNumber = number
    }

    println(counter)

}

fun part2(numbers: List<Int>) {

    var counter = 0

    var previousSum = Int.MAX_VALUE

    for (i in 0 until numbers.size - 2) {
        val sum = numbers[i] + numbers[i + 1] + numbers[i + 2]
        if (sum > previousSum) {
            counter++
        }
        previousSum = sum
    }

    println(counter)

}

fun main() {

    val numbers = File("src/main/kotlin/day1/day01_input.txt").readLines().map { it.toInt() }

    part1(numbers)

    part2(numbers)

}