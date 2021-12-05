package day04

import java.io.File

fun main() {
    val inputFile = File("src/main/kotlin/day04/day04_input.txt")
    val lines = inputFile.readLines().toMutableList()

    val numbers = lines.removeAt(0).split(",").map { it.toInt() }
    lines.removeAt(0)

    println(numbers)

    val bingoCards: MutableList<BingoCard> = mutableListOf()

    val rows: MutableList<MutableList<Int>> = mutableListOf()

    for (line in lines) {
        if (line.isBlank()) {
            bingoCards.add(createBingCard(rows))
            rows.clear()
        } else {
            val row = line.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toMutableList()
            rows.add(row)
        }
    }
//    process last bing card
    bingoCards.add(createBingCard(rows))

    println(bingoCards)

    determineWinners(numbers, bingoCards)

}

fun determineWinners(numbers: List<Int>, bingoCards: List<BingoCard>) {
    val finishedBingoCards: MutableList<BingoCard> = mutableListOf()
    val remainingBingoCards: MutableList<BingoCard> = bingoCards.toMutableList()
    for (number in numbers) {
        for (bingoCard in remainingBingoCards) {
            for (row in bingoCard.rows) {
                row.remove(number)
            }
            for (col in bingoCard.cols) {
                col.remove(number)
            }
            val emptyLists = bingoCard.rows.plus(bingoCard.cols).filter { it.isEmpty() }
            if (emptyLists.isNotEmpty()) {
                getScore(bingoCard, number)
                finishedBingoCards.add(bingoCard)
            }
        }
        remainingBingoCards.removeAll(finishedBingoCards)
    }
}

fun getScore(bingoCard: BingoCard, number: Int) {
    val cardScore = bingoCard.rows.flatten().sum()
    val totalScore = cardScore * number
    println("$cardScore * $number = $totalScore")
}

fun createBingCard(rows: MutableList<MutableList<Int>>): BingoCard {
    val cols: MutableList<MutableList<Int>> = mutableListOf()
    for (index in rows[0].indices) {
        val col: MutableList<Int> = mutableListOf()
        for (existingRow in rows) {
            col.add(existingRow[index])
        }
        cols.add(col)
    }
    return BingoCard(rows.toMutableList(), cols.toMutableList())
}
