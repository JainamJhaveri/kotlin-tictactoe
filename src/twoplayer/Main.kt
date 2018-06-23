package twoplayer

import Timber
import models.Turn
import models.BoardItemStatus

const val SQUARES = 9

fun main(args: Array<String>) = playGame()

// turn variable that determines which player has current turn
private var turn = Turn.P1

// bit-boards of player 1 and 2
private var p1 = 0
private var p2 = 0

fun playGame() {

    while (true) {
        println("twoplayer.turn of ${turn.name}: ")
        // take player input
        val input = readLine()!!.trim().toShort()
        handleBoardItemClicked((input - 1).toShort())
    }

}

/**
 * 1. gets bitboard of boardPosition
 * 2. gets playerboard
 * 3. store input bitboard into playerboard
 * 4. update game board visual
 * 5. declare winner/draw if any else switch turns
 *
 * @param boardPosition ranges from 0 to 8
 */
fun handleBoardItemClicked(boardPosition: Short) {
    // get bitboard from input
    val inputBitBoard = GameHelper.getBitBoardFrom(boardPosition)

    // get board of player whose turn is currently there
    var playerBoard = getBoardOf(turn)

    // store player input in player board
    playerBoard = playerBoard.storePlayerInput(inputBitBoard)

    // shows board visual
    updateGameBoardVisual(boardPosition)

    // check if a player is winner or game is over, else switch turns
    val isWinner = GameHelper.checkIfWinner(playerBoard)
    val isDraw = GameHelper.checkIfDraw(p1, p2)
    when {
        isWinner -> {
            Timber.e(turn.name + " wins")
            resetBoards()
        }
        isDraw -> {
            Timber.e("Game draw")
            resetBoards()
        }
    // player switch turns
        else -> switchTurns()
    }

}


private fun resetBoards() {
    p1 = 0
    p2 = 0
    turn = Turn.P1
//    view.resetBoard()
}


/**
 * prints game board
 */
private fun updateGameBoardVisual(boardPosition: Short) {
    val boardItemStatus: BoardItemStatus = when (turn) {
        Turn.P1 -> BoardItemStatus.X
        else -> BoardItemStatus.O
    }

//    view.updateViewForBoardPosition(boardPosition, boardItemStatus)
    printGameBoard(p1, p2)
}

private fun printGameBoard(p1: Int, p2: Int) {
    var displayString = "\n"
    val p1Padded = p1.getPaddedString()
    val p2Padded = p2.getPaddedString()
    p1Padded.forEachIndexed { index, c ->
        if (index % 3 == 0) displayString += "\n"
        displayString += when {
            c == '1' -> " x"
            p2Padded[index] == '1' -> " o"
            else -> " -"
        }
    }
}

/**
 * get board from Turn enum
 */
private fun getBoardOf(turn: Turn): Int = when (turn) {
    Turn.P1 -> p1
    Turn.P2 -> p2
}

/**
 * swaps turn variable
 */
private fun switchTurns() {
    turn = when (turn) {
        Turn.P1 -> Turn.P2
        Turn.P2 -> Turn.P1
    }
}

/**
 * 1. stores input bitboard in playerboard
 * 2. returns ORed playerBoard with inputBitBoard
 *
 * @param inputBitBoard bitboard of player
 * @return ORed playerBoard with inputBitBoard
 */
private fun Int.storePlayerInput(inputBitBoard: Int): Int {
    val newBoard = this or inputBitBoard
    when (turn) {
        Turn.P1 -> p1 = newBoard
        Turn.P2 -> p2 = newBoard
    }
    return newBoard
}


/**
 * checks if input is valid
 */
fun isInputValid(input: Short, inputBoard: Int): Boolean = when {
    input !in 1..9 -> {
        println("Invalid input, please enter number between 1 to 9")
        false
    }
    inputBoard and p1 != 0 || inputBoard and p2 != 0 -> {
        println("Your position is already taken, please enter some other input")
        false
    }
    else -> true
}


private fun Int.getPaddedString(): String = toString(2).padStart(SQUARES, '0').reversed()
