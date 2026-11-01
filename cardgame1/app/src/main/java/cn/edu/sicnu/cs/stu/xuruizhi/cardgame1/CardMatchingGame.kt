package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1

class CardMatchingGame(private val count: Int) {
    var score = 0
        private set
    private val cards: MutableList<Card> = mutableListOf()
    val cardCount: Int get() = cards.size

    companion object {
        const val MISMATCH_PENALTY = 2
        const val MATCH_BONUS = 4
        const val COST_TO_CHOOSE = 1
        const val MAX_CHOSEN = 3  // 每次选 3 张牌进行匹配
    }

    init {
        dealCards()
    }

    private fun dealCards() {
        val deck = Deck()
        cards.clear()
        for (i in 1..count) {
            val card: Card? = deck.drawRandomCard()
            if (card != null) {
                cards.add(card)
            }
        }
    }

    fun cardAtIndex(index: Int): Card {
        return cards[index]
    }

    /**
     * 重置游戏：重新发牌，分数归零。
     */
    fun reset() {
        score = 0
        dealCards()
    }

    fun chooseCardAtIndex(index: Int) {
        val card = cardAtIndex(index)
        if (card.isMatched) return

        if (card.isChosen) {
            // 取消选择
            card.isChosen = false
        } else {
            // 收集当前已选中的牌
            val chosenCards = cards.filter { it.isChosen && !it.isMatched }

            if (chosenCards.size >= MAX_CHOSEN - 1) {
                // 已有 2 张选中，当前是第 3 张 → 执行匹配判定
                val others = arrayOf(chosenCards[0], chosenCards[1])
                val matchScore = card.match(others)
                if (matchScore > 0) {
                    score += matchScore * MATCH_BONUS
                    card.isMatched = true
                    chosenCards[0].isMatched = true
                    chosenCards[1].isMatched = true
                } else {
                    score -= MISMATCH_PENALTY
                    chosenCards[0].isChosen = false
                    chosenCards[1].isChosen = false
                }
                card.isChosen = false
            } else {
                // 还没到 3 张，直接选中
                for (otherCard in chosenCards) {
                    if (otherCard.isChosen && !otherCard.isMatched) {
                        // 取消旧选中的逻辑（匹配 2 张时）
                    }
                }
            }
            score -= COST_TO_CHOOSE
            card.isChosen = true
        }
    }
}
