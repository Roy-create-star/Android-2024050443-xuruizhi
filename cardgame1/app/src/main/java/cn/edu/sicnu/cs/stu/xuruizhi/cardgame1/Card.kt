package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1


class Card(val suit: String, val rank: String, var isChosen:Boolean = false, var isMatched:Boolean = false)  {
    companion object {
        val rankStrings = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        val validSuits = arrayOf("\u2665", "\u2666", "\u2660", "\u2663")
    }

    override fun toString(): String {
        return suit + rank
    }

    /**
     * 3 张牌匹配逻辑：
     * otherCards 传入另外 2 张已选中的牌（连同自身共 3 张）。
     * - 3 张同点数 → 返回 6
     * - 3 张同花色 → 返回 3
     * - 恰好 2 张同点数 → 返回 2
     * - 恰好 2 张同花色 → 返回 1
     * - 无匹配 → 返回 0
     */
    fun match(otherCards: Array<Card>): Int {
        if (otherCards.size != 2) return 0
        val three = listOf(this, otherCards[0], otherCards[1])
        val ranks = three.map { it.rank }
        val suits = three.map { it.suit }

        // 3 张同点数
        if (ranks[0] == ranks[1] && ranks[1] == ranks[2]) return 6
        // 3 张同花色
        if (suits[0] == suits[1] && suits[1] == suits[2]) return 3
        // 恰好 2 张同点数
        if (ranks[0] == ranks[1] || ranks[0] == ranks[2] || ranks[1] == ranks[2]) return 2
        // 恰好 2 张同花色
        if (suits[0] == suits[1] || suits[0] == suits[2] || suits[1] == suits[2]) return 1
        return 0
    }
}
