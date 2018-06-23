import logic.Score

object GameHelper {
    // array of all winning combinations
    private val WINNING_COMBOS: List<Int> = getWinningCombos()

    // combination of game over
    private val GAME_OVER = "111111111".toInt(2)

    // Returns true if playerBoard (bitboard) is a winner's bitboard
    // false otherwise
    fun checkIfWinner(playerBoard: Int): Boolean {
        WINNING_COMBOS.forEach { if (it.and(playerBoard) == it) return true }
        return false
    }

    fun checkIfDraw(p1: Int, p2: Int): Boolean = p1 or p2 == GAME_OVER

    fun getBitBoardFrom(shifts: Short): Int = 1.shl(shifts.toInt())

    private fun getWinningCombos(): List<Int> {

        // 3 columns
        val firstCol: Int = "100100100".toInt(2)
        val secondCol: Int = "010010010".toInt(2)
        val thirdCol: Int = "001001001".toInt(2)

        // 3 rows
        val firstRow: Int = "111000000".toInt(2)
        val secondRow: Int = "000111000".toInt(2)
        val thirdRow: Int = "000000111".toInt(2)

        // 2 diagonals
        val firstDia: Int = "100010001".toInt(2)
        val secondDia: Int = "001010100".toInt(2)

        return listOf(firstCol, secondCol, thirdCol,
                firstRow, secondRow, thirdRow,
                firstDia, secondDia)
    }

    /**
     * returns score of the board from player 1's perspective
     *
     * @param p1 bitboard of player 1
     * @param p2 bitboard of player 2
     */
    fun evaluate(p1: Int, p2: Int, depth: Int): Score = when {
        checkIfDraw(p1, p2) -> Score(true, 0)
        checkIfWinner(p1) -> Score(true, 10 - depth)
        checkIfWinner(p2) -> Score(true, -10 + depth)
        else -> Score(false)
    }

    fun getPossibleMoves(p1: Int, p2: Int): List<Int> {
        var unfilledBoard = GAME_OVER - (p1 or p2)
        val remainingMoves = mutableListOf<Int>()

        var shifts = 0
        while (unfilledBoard != 0 && shifts < 9) {
            val possibleNum = 1.shl(shifts)
            if (unfilledBoard and possibleNum != 0) {
                unfilledBoard -= possibleNum // for faster convergence
                remainingMoves.add(possibleNum)
            }
            shifts++
        }

        return remainingMoves
    }

}