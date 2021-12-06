package day06

import java.io.File

fun main() {
    val numbers = File("src/main/kotlin/day06/day06_input.txt").readLines()[0].split(",").map { it.toInt() }

    val numberResults: MutableMap<Int, Long> = mutableMapOf()

    var totalFish: Long = 0

    for (number in numbers) {
        if (numberResults.containsKey(number)) {
            totalFish += numberResults[number]!!
        } else {
            val resultNumber: Long = calculateFishNumberRec(number, 256)
            numberResults[number] = resultNumber
            totalFish += resultNumber
        }
    }

    println(numberResults)

    println(totalFish)
}

fun calculateFishNumberRec(number: Int, cycles: Int): Long {
    if (cycles == 0) {
        return 1
    }
    return if (number == 0) {
        calculateFishNumberRec(6, cycles - 1) + calculateFishNumberRec(8, cycles - 1)
    } else {
        calculateFishNumberRec(number - 1, cycles - 1)
    }
}

//worked for part 1 but not part 2, heap space problems
fun calculateFishNumber(number: Int, cycles: Int): Int {
    val fishList: MutableList<Int> = mutableListOf(number)

    for (i in 1..cycles) {
        println("Cycle: $i, Size: ${fishList.size}")
        val newFishList: MutableList<Int> = mutableListOf()
        for (fish in fishList) {
            if (fish == 0) {
                newFishList.add(6)
                newFishList.add(8)
            } else {
                newFishList.add(fish - 1)
            }
        }
        fishList.clear()
        fishList.addAll(newFishList)

    }

    return fishList.size
}