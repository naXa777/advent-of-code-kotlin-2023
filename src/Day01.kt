fun main() {
    fun part1(input: List<String>): Int {
        val nums = input.map { s ->
            val first = s.first { it.isDigit() }.digitToInt()
            val last = s.last { it.isDigit() }.digitToInt()
            10 * first + last
        }
        return nums.sum()
    }

    fun part2(input: List<String>): Int {
        val map = mapOf(
            "0" to 0,
            "1" to 1,
            "2" to 2,
            "3" to 3,
            "4" to 4,
            "5" to 5,
            "6" to 6,
            "7" to 7,
            "8" to 8,
            "9" to 9,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )
        val matchWords = Regex(map.keys.joinToString("|"))

        val nums = input.map { s ->
            val allMatches = matchWords.findAll(s)

            if (allMatches.any()) {
                val firstWord = allMatches.first().value
                val lastWord = allMatches.last().value
                val first = map[firstWord]!!
                val last = map[lastWord]!!

                // s.println()
                // println("first $firstWord last $lastWord = ${10 * first + last}")

                10 * first + last
            } else {
                0
            }
        }

        return nums.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    part1(testInput).println()

    val input = readInput("Day01")
    part1(input).println()

    val testInput2 = readInput("Day01b_test")
    check(part2(testInput2) == 281)

    val input2 = readInput("Day01")
    part2(input2).println()
}
