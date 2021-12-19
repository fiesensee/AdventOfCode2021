package day_16

import java.io.File

data class Packet(val typeID: Int, val version: Int, val value: String, val subPackets: List<Packet>)

fun parseInput(): String {
    val hexValues = File("src/main/kotlin/day_16/day16_input.txt").readLines()[0].chunked(1)
        .map { it.toInt(16) }.map { String.format("%4s", Integer.toBinaryString(it)).replace(' ', '0') }

    return hexValues.joinToString("")
}

fun main() {
    val input = parseInput()

    val (rootPacket, bitsProcessed) = processPackets(input)

    println(rootPacket)

    val part1 = calculateVersionSum(rootPacket)
    println("Part 1: $part1")

    val part2 = runPacket(rootPacket)
    println("Part 2: $part2")
}

fun runPacket(packet: Packet): Long {
    when (packet.typeID) {
        0 -> {
            var sum: Long = 0
            for (subPacket in packet.subPackets) {
                sum += runPacket(subPacket)
            }
            return sum
        }
        1 -> {
            var product: Long = 1
            for (subPacket in packet.subPackets) {
                product *= runPacket(subPacket)
            }
            return product
        }
        2 -> {
            val values = mutableListOf<Long>()
            for (subPacket in packet.subPackets) {
                values.add(runPacket(subPacket))
            }
            return values.minOrNull()!!
        }
        3 -> {
            val values = mutableListOf<Long>()
            for (subPacket in packet.subPackets) {
                values.add(runPacket(subPacket))
            }
            return values.maxOrNull()!!
        }
        4 -> {
            return packet.value.toLong(2)
        }
        5 -> {
            return if (runPacket(packet.subPackets[0]) > runPacket(packet.subPackets[1])) 1 else 0
        }
        6 -> {
            return if (runPacket(packet.subPackets[0]) < runPacket(packet.subPackets[1])) 1 else 0
        }
        7 -> {
            return if (runPacket(packet.subPackets[0]) == runPacket(packet.subPackets[1])) 1 else 0
        }
        else -> {
            return 0
        }
    }
}

fun calculateVersionSum(packet: Packet): Int {
    return if (packet.subPackets.isEmpty()) {
        packet.version
    } else {
        var versionSum = packet.version
        for (subPacket in packet.subPackets) {
            versionSum += calculateVersionSum(subPacket)
        }
        versionSum
    }
}

fun processPackets(input: String): Pair<Packet, Int> {
    var workInput = input
    if (workInput.all { it == '0' }) {
        println("All zeros")
    }
    val version = workInput.substring(0, 3).toInt(2)
    val typeID = workInput.substring(3, 6).toInt(2)
    workInput = workInput.substring(6)

//    process literal packet
    if (typeID == 4) {
        var bitsProcessed = 6
        var value = ""
        var currentByte = workInput.substring(0, 5)
        var currentByteOffset = 0
        while (true) {
            value += currentByte.substring(1)
            bitsProcessed += 5
            currentByteOffset += 5
            if (currentByte.substring(0, 1) == "0") {
                break
            }
            currentByte = workInput.substring(currentByteOffset, currentByteOffset + 5)
        }
        return Pair(Packet(typeID, version, value, listOf()), bitsProcessed)
    } else {
        val lengthTypeID = workInput.substring(0, 1)
        if (lengthTypeID == "0") {
            val length = workInput.substring(1, 16).toInt(2)
            workInput = workInput.substring(16)

            val subPackets = mutableListOf<Packet>()

            var bitsProcessed = 0
            while (bitsProcessed < length) {
                val (packet, packetBits) = processPackets(workInput)
                bitsProcessed += packetBits
                workInput = workInput.substring(packetBits)
                subPackets.add(packet)
            }
            return Pair(Packet(typeID, version, "", subPackets), bitsProcessed + 22)
        } else if (lengthTypeID == "1") {
            val length = workInput.substring(1, 12).toInt(2)
            workInput = workInput.substring(12)

            val subPackets = mutableListOf<Packet>()

            var bitsProcessed = 0

            while (subPackets.size < length) {
                val (packet, packetBits) = processPackets(workInput)
                bitsProcessed += packetBits
                workInput = workInput.substring(packetBits)
                subPackets.add(packet)
            }
            return Pair(Packet(typeID, version, "", subPackets), bitsProcessed + 18)
        }
    }

    return Pair(Packet(version, typeID, "", listOf()), 0)
}