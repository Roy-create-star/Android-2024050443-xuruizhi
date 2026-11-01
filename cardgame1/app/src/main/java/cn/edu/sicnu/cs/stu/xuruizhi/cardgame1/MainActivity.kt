package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var tvName:TextView
    lateinit var tvStuId:TextView
    lateinit var tvGender:TextView
    lateinit var tvPhone:TextView
    lateinit var tvEmail:TextView
    lateinit var tvHobby:TextView
    lateinit var tvHighScore:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvName = findViewById(R.id.tv_name)
        tvStuId = findViewById(R.id.tv_stuid)
        tvGender = findViewById(R.id.tv_gender)
        tvPhone = findViewById(R.id.tv_phone)
        tvEmail = findViewById(R.id.tv_email)
        tvHobby = findViewById(R.id.tv_hobby)
        tvHighScore = findViewById(R.id.tv_highscore)

        findViewById<Button>(R.id.btn_edit_info).setOnClickListener {
            startActivity(Intent(this, EditInfoActivity::class.java))
        }
        findViewById<Button>(R.id.btn_start_game).setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        tvName.text = getString(R.string.text_name, SPUtil.getString(this, SPUtil.KEY_NAME))
        tvStuId.text = getString(R.string.text_stuid, SPUtil.getString(this, SPUtil.KEY_STU_ID))
        tvGender.text = getString(R.string.text_gender, SPUtil.getString(this, SPUtil.KEY_GENDER))
        tvPhone.text = getString(R.string.text_phone, SPUtil.getString(this, SPUtil.KEY_PHONE))
        tvEmail.text = getString(R.string.text_email, SPUtil.getString(this, SPUtil.KEY_EMAIL))
        tvHobby.text = getString(R.string.text_hobby, SPUtil.getString(this, SPUtil.KEY_HOBBY))
        tvHighScore.text = getString(R.string.text_highscore, SPUtil.getInt(this, SPUtil.KEY_HIGH_SCORE))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_about_title)
                    .setMessage(R.string.dialog_about_message)
                    .setPositiveButton(R.string.dialog_ok, null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
