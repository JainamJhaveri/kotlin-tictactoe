package test.logic

import GameHelper
import logic.Score
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GameHelperTest {

    @Nested
    inner class `evaluation function` {

        @org.junit.jupiter.api.Test
        fun `evaluation function should return 0 if game is draw`() {
            val p1 = "101001110".toInt(2)
            val p2 = "010110001".toInt(2)
            assertEquals(GameHelper.evaluate(p1, p2, 0), Score(true, 0))
        }

        @org.junit.jupiter.api.Test
        fun `evaluation function should return 10 if game is won at depth 0`() {
            val p1 = "111000000".toInt(2)
            val p2 = "000110000".toInt(2)
            assertEquals(GameHelper.evaluate(p1, p2, 0), Score(true, 10))
        }

        @org.junit.jupiter.api.Test
        fun `evaluation function should return 8 if game is won at depth 2`() {
            val p1 = "111000000".toInt(2)
            val p2 = "000110000".toInt(2)
            assertEquals(GameHelper.evaluate(p1, p2, 2), Score(true, 8))
        }

        @org.junit.jupiter.api.Test
        fun `evaluation function should return -10 if game is lost at depth 0`() {
            val p1 = "000110001".toInt(2)
            val p2 = "111000000".toInt(2)
            assertEquals(GameHelper.evaluate(p1, p2, 0), Score(true, -10))
        }

        @org.junit.jupiter.api.Test
        fun `evaluation function should return -2 if game is lost at depth 8`() {
            val p1 = "000110001".toInt(2)
            val p2 = "111000000".toInt(2)
            assertEquals(GameHelper.evaluate(p1, p2, 8), Score(true, -2))
        }

        @org.junit.jupiter.api.Test
        fun `evaluation function should return isGameOver=false when game is still not over`() {
            val p1 = "000110000".toInt(2)
            val p2 = "110000000".toInt(2)
            assertEquals(GameHelper.evaluate(p1, p2, 0), Score(false))
        }
    }


    @Nested
    inner class `possible moves from a given board state` {
        @Test
        fun `should return valid possible number of moves`() {
            val p1 = "110000000".toInt(2)
            val p2 = "001100000".toInt(2)
            val possibleMoves = GameHelper.getPossibleMoves(p1, p2)
            assertEquals(possibleMoves, listOf(1, 2, 4, 8, 16))
        }

        @Test
        fun `should return all possible number of moves if both players have no moves`() {
            val p1 = "000000000".toInt(2)
            val p2 = "000000000".toInt(2)
            val possibleMoves = GameHelper.getPossibleMoves(p1, p2)
            assertEquals(possibleMoves, listOf(1, 2, 4, 8, 16, 32, 64, 128, 256))
        }

        @Test
        fun `should return empty move list if no moves are remaining`() {
            val p1 = "111100000".toInt(2)
            val p2 = "000011111".toInt(2)
            val possibleMoves = GameHelper.getPossibleMoves(p1, p2)
            assertEquals(possibleMoves, emptyList<Int>())
        }
    }

    @Nested
    inner class `isWinner function` {

        @Test
        fun `should return true if the bitboard is of winner`() {
            val p1 = "111000000".toInt(2)
            assertEquals(true, GameHelper.checkIfWinner(p1))
        }

        @Test
        fun `should return false if the bitboard is not a winner`() {
            val p1 = "110100000".toInt(2)
            assertEquals(false, GameHelper.checkIfWinner(p1))
        }

    }

    @Nested
    inner class `isDraw function` {
        @Test
        fun `should return true if game is draw`() {
            val p1 = "110001101".toInt(2)
            val p2 = "001110010".toInt(2)
            assertEquals(true, GameHelper.checkIfDraw(p1, p2))
        }

        @Test
        fun `should return false if game is not draw`() {
            val p1 = "110000000".toInt(2)
            val p2 = "000011000".toInt(2)
            assertEquals(false, GameHelper.checkIfDraw(p1, p2))
        }

    }


}