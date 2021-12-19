package day_17

import kotlin.math.abs


//    test input
//const val targetXLow = 20
//const val targetXHigh = 30
//const val targetYLow = -10
//const val targetYHigh = -5
//    real input
const val targetXLow = 201
const val targetXHigh = 230
const val targetYLow = -99
const val targetYHigh = -65

fun main() {

//    part 1 is the triangular number of the lower y bound - 1


    var possibleTrajectories = mutableSetOf<Pair<Int, Int>>()
    for (x in 0..targetXHigh + 2) {
        for (y in targetYLow - 1..abs(targetYLow)) {
            if (checkStartTrajectory(x, y, 0, 0)) {
                possibleTrajectories.add(Pair(x, y))
            }
        }
    }

    println(possibleTrajectories)
    println("Part 2: ${possibleTrajectories.size}")

}

fun checkStartTrajectory(accX: Int, accY: Int, currentX: Int, currentY: Int): Boolean {
    if (currentX in targetXLow..targetXHigh && currentY in targetYLow..targetYHigh) {
        return true
    } else if (currentX > targetXHigh || currentY < targetYLow) {
        return false
    }

    val newX = currentX + accX
    val newY = currentY + accY
    var newXAcc = accX
    if (accX > 0) {
        newXAcc = accX - 1
    }
    val newYAcc = accY - 1

    return checkStartTrajectory(newXAcc, newYAcc, newX, newY)
}