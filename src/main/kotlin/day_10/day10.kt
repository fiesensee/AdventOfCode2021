package day_10

import java.io.File

val bracketMap = mapOf(
    ')' to '(',
    ']' to '[',
    '}' to '{',
    '>' to '<'
)

val bracketMapReversed = bracketMap.entries.associateBy({ it.value }, { it.key })

val scoreMap = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)

val part2ScoreMap = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4
)

fun main() {
    val lines = File("src/main/kotlin/day_10/day10_input.txt").readLines()

    val corruptedLines = mutableListOf<String>()

    var part1Score = 0

    for (line in lines) {
        val stack = mutableListOf<Char>()
        for (char in line) {
            if (char in bracketMap.keys) {
                if (stack.last() == bracketMap[char]) {
                    stack.removeAt(stack.lastIndex)
                } else {
                    corruptedLines.add(line)
                    part1Score += scoreMap[char]!!
                    break
                }
            } else {
                stack.add(char)
            }
        }
    }

    println("Part 1 Score: $part1Score")

    val incompleteLines = lines - corruptedLines

    val missingLines = mutableListOf<List<Char>>()

    for (line in incompleteLines) {
        val stack = mutableListOf<Char>()
        val missingChars = mutableListOf<Char>()
        for (char in line) {
            if (char in bracketMap.keys) {
                if (stack.last() == bracketMap[char]) {
                    stack.removeAt(stack.lastIndex)
                }
            } else {
                stack.add(char)
            }
        }
        val reversedStack = stack.reversed()
        for (char in reversedStack) {
            if (char in bracketMapReversed.keys) {
                missingChars += bracketMapReversed[char]!!
            }
        }
        missingLines.add(missingChars)
    }

    val part2Scores = mutableListOf<Long>()

    for (line in missingLines) {
        var score: Long = 0
        for (char in line) {
            score *= 5
            score += part2ScoreMap[char]!!
        }
        part2Scores.add(score)
    }

    println("Part 2 score: ${part2Scores.sorted()[(part2Scores.size / 2)]}")
}