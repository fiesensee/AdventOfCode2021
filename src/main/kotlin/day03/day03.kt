package day03

import java.io.File
import java.lang.Integer.parseInt

fun part1(values: List<String>) {
    val gammaRate: MutableList<String> = mutableListOf()
    val epsilonRate: MutableList<String> = mutableListOf()

    for (position in values[0].indices) {
        var balance = 0
        for (value in values) {
            if (value[position] == '0') {
                balance -= 1
            } else if (value[position] == '1') {
                balance += 1
            }
        }
        if (balance > 0) {
            gammaRate.add("1")
            epsilonRate.add("0")
        } else {
            gammaRate.add("0")
            epsilonRate.add("1")
        }
    }

    println(gammaRate.joinToString(""))
    println(epsilonRate.joinToString(""))

    val gammaValue = parseInt(gammaRate.joinToString(""), 2)
    val epsilonValue = parseInt(epsilonRate.joinToString(""), 2)

    println(gammaValue * epsilonValue)
}

fun getBalance(values: List<String>, position: Int): Int {
    var balance = 0
    for (value in values) {
        if (value[position] == '0') {
            balance -= 1
        } else if (value[position] == '1') {
            balance += 1
        }
    }
    return balance
}

fun part2(values: List<String>) {

    var oxyValues = values.toMutableList()

    for (position in values[0].indices) {
        if (oxyValues.size == 1) {
            break
        }
        val balance = getBalance(oxyValues, position)
        val targetValue = if (balance >= 0) '1' else '0'

        val remaingValues = mutableListOf<String>()
        for (value in oxyValues) {
            if (value[position] == targetValue) {
                remaingValues.add(value)
            }
        }
        oxyValues = remaingValues
    }

    var co2Values = values.toMutableList()

    for (position in values[0].indices) {
        if (co2Values.size == 1) {
            break
        }
        val balance = getBalance(co2Values, position)
        val targetValue = if (balance >= 0) '0' else '1'

        val remainingValues = mutableListOf<String>()
        for (value in co2Values) {
            if (value[position] == targetValue) {
                remainingValues.add(value)
            }
        }
        co2Values = remainingValues
    }

    val oxyValue = parseInt(oxyValues[0], 2)
    val co2Value = parseInt(co2Values[0], 2)
    println("$oxyValue x $co2Value = ${oxyValue * co2Value}")



}

fun main() {
    val input: List<String> = File("src/main/kotlin/day03/day03_input.txt").readLines()

    part1(input)

    part2(input)


}