data class Round(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0
) {
    companion object {
        private fun parseNumberBefore(s: String, pos: Int): Int {
            return if (pos > 0) {
                val redIdxStart = s.substring(0, pos).lastIndexOf(" ")
                s.substring(redIdxStart + 1, pos).toInt()
            } else {
                0
            }
        }

        fun parseFrom(rs: String): Round {
            val redIdx = rs.indexOf(" red")
            val greenIdx = rs.indexOf(" green")
            val blueIdx = rs.indexOf(" blue")

            val red = parseNumberBefore(rs, redIdx)
            val green = parseNumberBefore(rs, greenIdx)
            val blue = parseNumberBefore(rs, blueIdx)

            return Round(
                red = red,
                green = green,
                blue = blue,
            )
        }
    }

    fun power(): Int = this.red * this.green * this.blue
}

data class Game(
    val id: Int,
    val rounds: List<Round>
) {
    companion object {
        fun parseFrom(gs: String): Game {
            val idStart = gs.indexOf(' ')
            val idEnd = gs.indexOf(':')
            val id = gs.substring(idStart + 1, idEnd).toInt()
            val content = gs.substring(idEnd + 1).split(';')
            return Game(
                id = id,
                rounds = content.map { rs -> Round.parseFrom(rs) }
            )
        }
    }

    fun fewestNumberOfCubesPossible() = Round(
        red = this.rounds.maxOf { it.red },
        green = this.rounds.maxOf { it.green },
        blue = this.rounds.maxOf { it.blue },
    )
}

data class Constraints(
    val red: Int,
    val green: Int,
    val blue: Int
) {
    fun isGamePossible(game: Game): Boolean {
        return game.rounds.all { round ->
            round.red <= this.red && round.green <= this.green && round.blue <= this.blue
        }
    }
}

fun main() {
    fun part1(input: List<String>, constrains: Constraints): Int {
        val games = input.map(Game::parseFrom)
        return games.filter { constrains.isGamePossible(it) }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val games = input.map(Game::parseFrom)
        return games.map { it.fewestNumberOfCubesPossible() }
            .sumOf { it.power() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02/part1_test")
    val constrains = Constraints(
        red = 12,
        green = 13,
        blue = 14
    )
    part1(testInput, constrains).also { ans ->
        ans.println()
        check(ans == 8)
    }

    val input = readInput("Day02/input")
    part1(input, constrains).also { ans ->
        ans.println()
        check(ans == 1734)
    }

    part2(testInput).also { ans ->
        ans.println()
        check(ans == 2286)
    }

    part2(input).also { ans ->
        ans.println()
        check(ans == 70387)
    }
}
