package day_14

import day_11.getGrid
import java.io.File

data class Reaction(val input: String, val output: String)
data class Insertion(val place: Int, val value: String)

fun parseInput(): Pair<String, List<Reaction>> {
    val lines = File("src/main/kotlin/day_14/day14_input.txt").readLines()
    val start = lines[0]
    val reactions = mutableListOf<Reaction>()

    for (line in lines.subList(2, lines.size)) {
        val values = line.split(" -> ")
        reactions.add(Reaction(values[0], values[1]))
    }

    return Pair(start, reactions)
}

fun main() {

    val (start, reactions) = parseInput()

    println(start)
    println(reactions)

    val steps = 10
    var currentState = start

    for (i in 0 until steps) {
        println("Step $i")
        var insertions = mutableListOf<Insertion>()
        var oldState = currentState
        for (reaction in reactions) {
            for ((index, char) in oldState.withIndex()) {
                if (index == oldState.length - 1) {
                    continue
                }
                if (char == reaction.input[0] && oldState[index + 1] == reaction.input[1]) {
                    insertions.add(Insertion(index + 1, reaction.output))
                }
            }
        }

        for ((index, insertion) in insertions.sortedBy { it.place }.withIndex()) {
            val start = insertion.place + index
            val end = insertion.place + index
            currentState = currentState.substring(0, start) + insertion.value + currentState.substring(end)
        }

    }

    // count occurrences of letters in the current state
    val frequencyMap = currentState.groupingBy { it }.eachCount()
    println(frequencyMap)
    println("Part 1: ${frequencyMap.values.maxOrNull()?.minus(frequencyMap.values.minOrNull()!!)}")


    // Part 2
    val combinationCounts = mutableMapOf<String, Long>()
    for ((index, char) in start.withIndex()) {
        if (index == start.length - 1) {
            continue
        }
        val combination = "${start[index]}${start[index + 1]}"
        combinationCounts[combination] = combinationCounts.getOrDefault(combination, 0) + 1
    }

    println(combinationCounts)

    val frequencyMap2: MutableMap<Char, Long> = start.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

    for (i in 1..40) {
        val newCombinations = combinationCounts.toMutableMap()
        for (reaction in reactions) {
            for (combination in combinationCounts.keys) {
                val count = combinationCounts[combination]!!
                if (combination == reaction.input && count > 0) {
                    val leftOutput = reaction.input[0] + reaction.output
                    val rightOutput = reaction.output + reaction.input[1]
                    newCombinations[leftOutput] = newCombinations.getOrDefault(leftOutput, 0) + count
                    newCombinations[rightOutput] = newCombinations.getOrDefault(rightOutput, 0) + count
                    newCombinations[reaction.input] = newCombinations.getOrDefault(reaction.input, 0) - count
                    frequencyMap2[reaction.output[0]] = frequencyMap2.getOrDefault(reaction.output[0], 0) + count
                }
            }
        }
        combinationCounts.clear()
        combinationCounts.putAll(newCombinations)
        println(frequencyMap2)
        println(combinationCounts)
        println(frequencyMap2.values.sum())
    }


    println(frequencyMap2)
    println("Part 2: ${frequencyMap2.values.maxOrNull()?.minus(frequencyMap2.values.minOrNull()!!)}")


}