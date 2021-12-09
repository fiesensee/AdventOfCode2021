package day08

import java.io.File

data class Entry(val wirings: List<List<String>>, val outputs: List<List<String>>)

fun main() {
    val inputLines = File("src/main/kotlin/day08/day08_input.txt").readLines()

    val entries = mutableListOf<Entry>()

    for (line in inputLines) {
        val rawLists = line.split(" | ")
        val wirings = rawLists[0].split(" ").map { it.chunked(1) }
        val outputs = rawLists[1].split(" ").map { it.chunked(1) }
        entries.add(Entry(wirings, outputs))
    }

    var part1Counter = 0

    for (entry in entries) {
        for (output in entry.outputs) {
            if (output.size in listOf(2, 3, 4, 7)) {
                part1Counter++
            }
        }
    }

    println("Part 1: $part1Counter")

    var totalSum = 0

    for (entry in entries) {

        val rightSidePair = determineRightSidePair(entry.wirings)
        val top = determineTop(entry.wirings)
        val bottom = determineBottom(entry.wirings, top)
        val bottomLeft = determineBottomLeft(entry.wirings, top)
        //        middle should be determined by 3 - top, rightsidepair and bottom, should come before topLeft
        val middle = determineMiddle(entry.wirings, top, rightSidePair, bottom)
//        topLeft should be determined by 9 - 3 minus top, rightSidePair, bottom, middle, should come after middle
        val topLeft = determineTopLeft(entry.wirings, top, rightSidePair, bottom, middle, bottomLeft)

        val topRight = determineTopRight(entry.wirings, top, topLeft, rightSidePair, middle, bottomLeft, bottom)
        val bottomRight = rightSidePair - topRight

        println(entry.wirings)
        println("Top: $top")
        println("Bottom: $bottom")
        println("Right Side Pair: $rightSidePair")
        println("Bottom left: $bottomLeft")
        println("Top left: $topLeft")
        println("Middle: $middle")
        println("Top Right: $topRight")
        println("Bottom Right: $bottomRight")

        val zero = top + topLeft + bottomLeft + bottom + bottomRight + topRight
        val one = topRight + bottomRight
        val two = top + topRight + middle + bottomLeft + bottom
        val three = top + topRight + middle + bottomRight + bottom
        val four = topLeft + topRight + middle + bottomRight
        val five = top + topLeft + middle + bottomRight + bottom
        val six = top + topLeft + middle + bottomRight + bottom + bottomLeft
        val seven = top + topRight + bottomRight
        val eight = top + topRight + bottomRight + middle + topLeft + bottomLeft + bottom
        val nine = top + topLeft + middle + topRight + bottomRight + bottom

        val numbers = mapOf<String, Set<String>>(
            "0" to zero.toSet(),
            "1" to one.toSet(),
            "2" to two.toSet(),
            "3" to three.toSet(),
            "4" to four.toSet(),
            "5" to five.toSet(),
            "6" to six.toSet(),
            "7" to seven.toSet(),
            "8" to eight.toSet(),
            "9" to nine.toSet()
        )

        var displayedNumber = ""

        for (output in entry.outputs) {
            for ((value, wirings) in numbers) {
                if (wirings == output.toSet()) {
                    displayedNumber += value
                    break
                }
            }
        }

        println(displayedNumber)
        totalSum += displayedNumber.toInt()
    }

    println("Part 2: $totalSum")

}

fun determineTopRight(
    wirings: List<List<String>>,
    top: List<String>,
    topLeft: List<String>,
    rightSidePair: List<String>,
    middle: List<String>,
    bottomLeft: List<String>,
    bottom: List<String>
): List<String> {
    val potentialSixWirings: List<List<String>> = getWiringByWireSize(wirings, 6)

    var result = listOf<String>()

    for (wiring in potentialSixWirings) {
        val subtractedWiring =
            wiring.filter { it !in top && it !in bottom && it !in bottomLeft && it !in topLeft && it !in middle }
        if (subtractedWiring.size == 1) {
            result = rightSidePair - subtractedWiring
        }
    }

    return result
}

fun determineMiddle(
    wirings: List<List<String>>,
    top: List<String>,
    rightSidePair: List<String>,
    bottom: List<String>
): List<String> {
    val possible3Wirings = getWiringByWireSize(wirings, 5)
    var result = listOf<String>()

    for (wiring in possible3Wirings) {
        val subtractedWiring =
            wiring.filter { it !in top && it !in rightSidePair && it !in bottom }
        if (subtractedWiring.size == 1) {
            result = subtractedWiring
        }
    }

    return result
}

fun determineTopLeft(
    wirings: List<List<String>>,
    top: List<String>,
    rightSidePair: List<String>,
    bottom: List<String>,
    middle: List<String>,
    bottomLeft: List<String>
): List<String> {
    var result = listOf<String>()

    for (wiring in wirings) {
        val subtractedWiring =
            wiring.filter { it !in top && it !in rightSidePair && it !in bottom && it !in middle && it !in bottomLeft }
        if (subtractedWiring.size == 1) {
            result = subtractedWiring
        }
    }

    return result
}

fun getWiringForUniqueNumber(wirings: List<List<String>>, number: Int): List<String> {
    var size = 0
    when (number) {
        1 -> size = 2
        4 -> size = 4
        7 -> size = 3
        8 -> size = 7
    }

    for (wiring in wirings) {
        if (wiring.size == size) {
            return wiring
        }
    }

    return mutableListOf()
}

fun getWiringByWireSize(wirings: List<List<String>>, size: Int): List<List<String>> {
    val result = mutableListOf<List<String>>()

    for (wiring in wirings) {
        if (wiring.size == size) {
            result.add(wiring)
        }
    }

    return result

}

fun determineRightSidePair(wirings: List<List<String>>): List<String> {
    return getWiringForUniqueNumber(wirings, 1)
}

fun determineTop(wirings: List<List<String>>): List<String> {
    val oneWiring = getWiringForUniqueNumber(wirings, 1)
    val sevenWiring = getWiringForUniqueNumber(wirings, 7)

    return sevenWiring.filter { it !in oneWiring }
}

fun determineBottom(wirings: List<List<String>>, topWire: List<String>): List<String> {
    val fourWiring = getWiringForUniqueNumber(wirings, 4)

    return findNine(wirings, fourWiring, topWire).filter { it !in fourWiring && it !in topWire }
}

fun findNine(wirings: List<List<String>>, fourWiring: List<String>, topWire: List<String>): List<String> {
    val potentialNineWirings = getWiringByWireSize(wirings, 6)

    var nineWiring = listOf<String>()

    for (wiring in potentialNineWirings) {
        val subtractedWiring = wiring.filter { it !in fourWiring && it !in topWire }
        if (subtractedWiring.size == 1) {
            nineWiring = wiring
            break
        }
    }

    return nineWiring
}

fun determineBottomLeft(wirings: List<List<String>>, topWire: List<String>): List<String> {
    val eightWiring = getWiringForUniqueNumber(wirings, 8)
    val fourWiring = getWiringForUniqueNumber(wirings, 4)
    val nineWiring = findNine(wirings, fourWiring, topWire)

    return eightWiring.filter { it !in nineWiring }
}

