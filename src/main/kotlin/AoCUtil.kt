import java.io.File

fun main() {
    val day = "12"
    val directory = File("src/main/kotlin/day_$day/")
    directory.mkdir()
    val codeFile = directory.resolve("day$day.kt")
    codeFile.createNewFile()
    directory.resolve("day${day}_test_input.txt").createNewFile()
    directory.resolve("day${day}_input.txt").createNewFile()
}