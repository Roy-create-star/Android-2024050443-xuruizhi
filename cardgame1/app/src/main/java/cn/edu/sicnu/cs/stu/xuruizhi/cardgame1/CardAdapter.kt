package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView 适配器，从 RecyclerView.Adapter 派生，
 * 实现回调处理（点击翻牌）。
 */
class CardAdapter(
    private val game: CardMatchingGame,
    private val cardBackText: String,
    private val onCardClicked: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnCard: Button = itemView.findViewById(R.id.btn_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = game.cardAtIndex(position)
        holder.btnCard.isEnabled = !card.isMatched
        if (card.isChosen || card.isMatched) {
            holder.btnCard.text = card.toString()
        } else {
            holder.btnCard.text = cardBackText
        }
        holder.btnCard.setOnClickListener {
            onCardClicked(position)
        }
    }

    override fun getItemCount(): Int = game.cardCount
}
