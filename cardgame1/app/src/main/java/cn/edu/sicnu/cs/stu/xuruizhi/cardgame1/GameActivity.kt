package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameActivity : AppCompatActivity() {
    private lateinit var game: CardMatchingGame
    private lateinit var rvCards: RecyclerView
    private lateinit var tvScore: TextView
    private lateinit var adapter: CardAdapter

    private val CARD_COUNT = 24

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        rvCards = findViewById(R.id.rv_cards)
        tvScore = findViewById(R.id.tv_score)

        game = CardMatchingGame(CARD_COUNT)
        setupRecyclerView()
        findViewById<Button>(R.id.btn_reset).setOnClickListener { resetGame() }
        findViewById<Button>(R.id.btn_exit).setOnClickListener { finish() }
    }

    private fun getSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 6 else 4
    }

    private fun setupRecyclerView() {
        val cardBackText = getString(R.string.card_back_text)
        adapter = CardAdapter(game, cardBackText) { position ->
            game.chooseCardAtIndex(position)
            refreshUI()
        }
        rvCards.layoutManager = GridLayoutManager(this, getSpanCount())
        rvCards.adapter = adapter
        refreshUI()
    }

    private fun resetGame() {
        game.reset()
        adapter.notifyDataSetChanged()
        refreshUI()
    }

    private fun refreshUI() {
        tvScore.text = getString(R.string.score_text, game.score)
        adapter.notifyDataSetChanged()
        val oldHigh = SPUtil.getInt(this, SPUtil.KEY_HIGH_SCORE)
        if (game.score > oldHigh) {
            SPUtil.putInt(this, SPUtil.KEY_HIGH_SCORE, game.score)
        }
    }
}
