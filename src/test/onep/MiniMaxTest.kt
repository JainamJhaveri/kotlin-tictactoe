package test.onep

import logic.MoveValue
import onep.MiniMax
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MiniMaxTest {

    @Nested
    inner class `alphaBetaMax function` {

        @Test
        fun `check if max function returns correct score for win in 1`() {
            val p1 = "110000000".toInt(2)
            val p2 = "000110000".toInt(2)
            assertEquals(MoveValue(64, 5), MiniMax.alphaBetaMax(p1, p2, Int.MIN_VALUE, Int.MAX_VALUE, 4))
        }

        @Test
        fun `check if max function returns correct score for win in 2`() {
            val p1 = "100000001".toInt(2)
            val p2 = "000010100".toInt(2)
            assertEquals(MoveValue(64, 3), MiniMax.alphaBetaMax(p1, p2, Int.MIN_VALUE, Int.MAX_VALUE, 4))
        }

        @Test
        fun `check if max function returns correct score for win in 3`() {
            val p1 = "100000000".toInt(2)
            val p2 = "010000000".toInt(2)

            // 2 possibilities, but algorithm will chose only first
            assertEquals(MoveValue(4, 3), MiniMax.alphaBetaMax(p1, p2, Int.MIN_VALUE, Int.MAX_VALUE, 2))
//            assertEquals(MoveValue(16, 3), MiniMax.alphaBetaMax(p1, p2, Int.MIN_VALUE, Int.MAX_VALUE, 2))
        }

    }

}