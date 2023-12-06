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
            "oneight" to 18,
            "twone" to 21,
            "threeight" to 38,
            "fiveight" to 58,
            "sevenine" to 79,
            "eightwo" to 82,
            "eighthree" to 83,
            "nineight" to 98,
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
            matchWords.replace(s) { mr -> map[mr.value].toString() }
        }

        return part1(nums)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01/part1_test")
    part1(testInput).also { ans ->
        ans.println()
        check(ans == 142)
    }

    val input = readInput("Day01/input")
    part1(input).also { ans ->
        ans.println()
        check(ans == 54632)
    }

    val testInput2 = readInput("Day01/part2_test")
    part2(testInput2).also { ans ->
        ans.println()
        check(ans == 281)
    }

    part2(input).also { ans ->
        ans.println()
        check(54019 == ans)
    }
}
