package models

import twoplayer.SQUARES

data class BoardItem(val position: Int,
                     var status: BoardItemStatus = BoardItemStatus.EMPTY) {
    companion object {
        fun getInitialList(): MutableList<BoardItem> {
            val items = mutableListOf<BoardItem>()
            for (i in 1..SQUARES) {
                items.add(BoardItem(i))
            }
            return items
        }
    }
}