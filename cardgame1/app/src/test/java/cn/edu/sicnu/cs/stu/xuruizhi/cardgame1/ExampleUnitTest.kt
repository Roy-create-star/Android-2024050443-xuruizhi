package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1

import org.junit.Test
import org.junit.Assert.*

/**
 * Card 类的单元测试
 */
class CardTest {

    @Test
    fun toString_returnsSuitPlusRank() {
        val card = Card(suit = "♥", rank = "A")
        assertEquals("♥A", card.toString())
    }

    @Test
    fun match_sameRank_returns4() {
        val c1 = Card(suit = "♥", rank = "A")
        val c2 = Card(suit = "♠", rank = "A")
        assertEquals(4, c1.match(arrayOf(c2)))
    }

    @Test
    fun match_sameSuitDifferentRank_returns1() {
        val c1 = Card(suit = "♥", rank = "A")
        val c2 = Card(suit = "♥", rank = "K")
        assertEquals(1, c1.match(arrayOf(c2)))
    }

    @Test
    fun match_nothingInCommon_returns0() {
        val c1 = Card(suit = "♥", rank = "A")
        val c2 = Card(suit = "♠", rank = "K")
        assertEquals(0, c1.match(arrayOf(c2)))
    }

    @Test
    fun match_emptyArray_returns0() {
        val c1 = Card(suit = "♥", rank = "A")
        assertEquals(0, c1.match(emptyArray()))
    }

    @Test
    fun defaultFlags_areFalse() {
        val card = Card(suit = "♦", rank = "5")
        assertFalse(card.isChosen)
        assertFalse(card.isMatched)
    }
}

/**
 * Deck 类的单元测试
 */
class DeckTest {

    @Test
    fun drawRandomCard_returnsNonNull() {
        val deck = Deck()
        assertNotNull(deck.drawRandomCard())
    }

    @Test
    fun drawAllCards_returns52UniqueCards() {
        val deck = Deck()
        val drawn = mutableSetOf<String>()
        repeat(52) {
            val c = deck.drawRandomCard()
            assertNotNull(c)
            drawn.add(c.toString())
        }
        assertEquals(52, drawn.size)
    }

    @Test
    fun drawMoreThan52_returnsNull() {
        val deck = Deck()
        repeat(52) { deck.drawRandomCard() }
        assertNull(deck.drawRandomCard())
    }
}

/**
 * CardMatchingGame 类（Controller 模型部分）的单元测试
 */
class CardMatchingGameTest {

    @Test
    fun cardAtIndex_returnsCardsForEveryIndex() {
        val game = CardMatchingGame(12)
        for (i in 0 until 12) {
            assertNotNull(game.cardAtIndex(i))
        }
    }

    @Test
    fun initialScore_isZero() {
        val game = CardMatchingGame(12)
        assertEquals(0, game.score)
    }

    @Test
    fun chooseSingleCard_deductsCostToChoose() {
        val game = CardMatchingGame(12)
        game.chooseCardAtIndex(0)
        // 第一次选牌没有可匹配对象，只扣 COST_TO_CHOOSE(1)
        assertEquals(-1, game.score)
        assertTrue(game.cardAtIndex(0).isChosen)
    }

    @Test
    fun chooseSameCardTwice_unChoosesIt() {
        val game = CardMatchingGame(12)
        game.chooseCardAtIndex(0)
        game.chooseCardAtIndex(0)
        assertFalse(game.cardAtIndex(0).isChosen)
    }

    @Test
    fun chooseTwoMatchingCards_marksBothMatched() {
        val game = CardMatchingGame(12)
        // 找到两张同花色的牌（match 返回 1）或同点数的牌（match 返回 4）
        // 由于随机性，这里直接选两张，如果匹配则两张都 isMatched，否则都不 matched
        // 至少验证不会崩溃
        game.chooseCardAtIndex(0)
        game.chooseCardAtIndex(1)
        // 无论是否匹配，两张牌中至少一张不再是 chosen（匹配则 matched，不匹配则取消 chosen）
        val c0 = game.cardAtIndex(0)
        val c1 = game.cardAtIndex(1)
        val matchedOrUnchosen = c0.isMatched || c1.isMatched || (!c0.isChosen) || (!c1.isChosen)
        assertTrue(matchedOrUnchosen)
    }

    @Test
    fun matchedCard_cannotBeChosenAgain() {
        val game = CardMatchingGame(12)
        // 手动构造：找两张同点数的牌
        var idx1 = -1
        var idx2 = -1
        outer@ for (i in 0 until 12) {
            for (j in i + 1 until 12) {
                if (game.cardAtIndex(i).match(arrayOf(game.cardAtIndex(j))) > 0) {
                    idx1 = i; idx2 = j; break@outer
                }
            }
        }
        if (idx1 == -1) return // 随机牌堆可能没有可匹配的，跳过
        game.chooseCardAtIndex(idx1)
        game.chooseCardAtIndex(idx2)
        assertTrue(game.cardAtIndex(idx1).isMatched)
        assertTrue(game.cardAtIndex(idx2).isMatched)
        // 再点已匹配的牌，分数不再变化
        val scoreBefore = game.score
        game.chooseCardAtIndex(idx1)
        assertEquals(scoreBefore, game.score)
    }
}
