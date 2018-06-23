package onep

import GameHelper
import logic.MoveValue

object MiniMax {


    private fun alphaBetaMin(p1: Int, p2: Int, alpha: Int, beta: Int, depth: Int): MoveValue {

        // if game is over at this point, return -(game score)
        val (isGameOver, score) = GameHelper.evaluate(p1, p2, depth)
        if (isGameOver) return MoveValue(value = score)

        // for each possible moves for p2 from this game state
        //   call alphaBetaMax function and find alphaBetaMin possible score
        val possibleMoves = GameHelper.getPossibleMoves(p1, p2)
        var beta1 = beta
        var resultantMove = 0

        possibleMoves.forEach { possibleMove ->
            // get board after 'p2' makes 'move'
            val boardAfterMove = possibleMove or p2

            // call alphaBetaMax for the new move
            val (_, value) = alphaBetaMax(p1, boardAfterMove, alpha, beta1, depth + 1)

            // cut-off this sub-tree
            if (value <= alpha) return MoveValue(possibleMove, alpha)

            // update beta value and resultant move
            if (value < beta1) {
                beta1 = value
                resultantMove = possibleMove
            }

        }

        return MoveValue(resultantMove, beta1)

    }

    fun alphaBetaMax(p1: Int, p2: Int, alpha: Int, beta: Int, depth: Int): MoveValue {

        // if game is over at this point, return game score
        val (isGameOver, score) = GameHelper.evaluate(p1, p2, depth)
        if (isGameOver) return MoveValue(value = score)

        // for each possible moves for p1 from this game state
        //   call alphaBetaMin function and find alphaBetaMax possible score
        val possibleMoves = GameHelper.getPossibleMoves(p1, p2)
        var alpha1 = alpha
        var resultantMove = 0

        possibleMoves.forEach { possibleMove ->
            // get board after 'p1' makes 'move'
            val boardAfterMove = possibleMove or p1

            // call alphaBetaMax for the new move
            val (_, value) = alphaBetaMin(boardAfterMove, p2, alpha1, beta, depth + 1)

            // cut-off this sub-tree
            if (value >= beta) return MoveValue(possibleMove, beta)

            // update beta value and resultant move
            if (value > alpha1) {
                alpha1 = value
                resultantMove = possibleMove
            }

        }

        return MoveValue(resultantMove, alpha1)

    }


}