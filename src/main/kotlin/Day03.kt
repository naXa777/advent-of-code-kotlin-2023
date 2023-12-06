fun main() {
    fun isPartNumber(input: List<String>, currNumStart: Pair<Int, Int>, currNumEnd: Pair<Int, Int>): Boolean {
        val i1 = (currNumStart.first - 1).coerceAtLeast(0)
        val j1 = (currNumStart.second - 1).coerceAtLeast(0)
        val scanFrom = i1 to j1
        val i2 = (currNumEnd.first + 1).coerceAtMost(input.size - 1)
        val j2 = (currNumEnd.second + 1).coerceAtMost(input[currNumEnd.first].length - 1)
        val scanTo = i2 to j2

        for (i in scanFrom.first..scanTo.first)
            for (j in scanFrom.second..scanTo.second)
                if (input[i][j] != '.' && !input[i][j].isDigit())
                    return true
        return false
    }

    fun findStarsAroundNumber(input: List<String>, currNumStart: Pair<Int, Int>, currNumEnd: Pair<Int, Int>): List<Pair<Int, Int>> {
        val i1 = (currNumStart.first - 1).coerceAtLeast(0)
        val j1 = (currNumStart.second - 1).coerceAtLeast(0)
        val scanFrom = i1 to j1
        val i2 = (currNumEnd.first + 1).coerceAtMost(input.size - 1)
        val j2 = (currNumEnd.second + 1).coerceAtMost(input[currNumEnd.first].length - 1)
        val scanTo = i2 to j2

        val result = mutableListOf<Pair<Int, Int>>()
        for (i in scanFrom.first..scanTo.first)
            for (j in scanFrom.second..scanTo.second)
                if (input[i][j] == '*')
                    result.add(i to j)
        return result
    }

    fun part1(input: List<String>): Int {
        val partNumbers = mutableListOf<Int>()
        var currNum = 0
        var currNumStart = 0 to 0
        var currNumEnd = 0 to 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j].isDigit()) {
                    val isFirstDigit = currNum == 0
                    if (isFirstDigit) {
                        currNumStart = i to j
                    }
                    currNum = currNum * 10 + input[i][j].digitToInt()

                    val isEndOfRow = (j == input[i].length - 1)
                    if (isEndOfRow) {
                        currNumEnd = i to j
                        if (isPartNumber(input, currNumStart, currNumEnd)) {
                            partNumbers.add(currNum)
                        }
                        currNum = 0
                    }
                } else if (input[i][j] == '.') {
                    val isDotAfterLastDigit = currNum != 0
                    if (isDotAfterLastDigit) {
                        currNumEnd = i to j - 1
                        if (isPartNumber(input, currNumStart, currNumEnd)) {
                            partNumbers.add(currNum)
                        }
                        currNum = 0
                    }
                } else {
                    val isSymbolAfterLastDigit = currNum != 0
                    if (isSymbolAfterLastDigit) {
                        partNumbers.add(currNum)
                        currNum = 0
                    }
                }
            }
        }

        return partNumbers.sum()
    }

    fun part2(input: List<String>): Long {
        // multi value map of stars to adjacent numbers
        val starSymbols = HashMap<Pair<Int, Int>, MutableList<Int>>()
        var currNum = 0
        var currNumStart = 0 to 0
        var currNumEnd = 0 to 0
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j].isDigit()) {
                    val isFirstDigit = currNum == 0
                    if (isFirstDigit) {
                        currNumStart = i to j
                    }
                    currNum = currNum * 10 + input[i][j].digitToInt()

                    val isEndOfRow = (j == input[i].length - 1)
                    if (isEndOfRow) {
                        currNumEnd = i to j
                        val stars = findStarsAroundNumber(input, currNumStart, currNumEnd)
                        stars.forEach { pos ->
                            starSymbols.getOrPut(pos) { mutableListOf() }.add(currNum)
                        }
                        currNum = 0
                    }
                } else if (input[i][j] == '.') {
                    val isDotAfterLastDigit = currNum != 0
                    if (isDotAfterLastDigit) {
                        currNumEnd = i to j - 1
                        val stars = findStarsAroundNumber(input, currNumStart, currNumEnd)
                        stars.forEach { pos ->
                            starSymbols.getOrPut(pos) { mutableListOf() }.add(currNum)
                        }
                        currNum = 0
                    }
                } else {
                    val isSymbolAfterLastDigit = currNum != 0
                    if (isSymbolAfterLastDigit) {
                        val stars = findStarsAroundNumber(input, currNumStart, currNumEnd)
                        stars.forEach { pos ->
                            starSymbols.getOrPut(pos) { mutableListOf() }.add(currNum)
                        }
                        currNum = 0
                    }
                }
            }
        }

        val gears = starSymbols.filter { it.value.size == 2 }.values
        return gears.sumOf { it.first().toLong() * it.last().toLong() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03/part1_test")
    part1(testInput).also { ans ->
        ans.println()
        check(ans == 4361)
    }

    val input = readInput("Day03/input")
    part1(input).also { ans ->
        ans.println()
        check(ans == 530495)
    }

    part2(testInput).also { ans ->
        ans.println()
        check(ans == 467835L)
    }

    part2(input).also { ans ->
        ans.println()
        check(ans > 65781123L) { "your answer is too low" }
    }
}
