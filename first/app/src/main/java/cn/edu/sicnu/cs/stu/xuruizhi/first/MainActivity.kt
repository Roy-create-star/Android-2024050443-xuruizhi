package cn.edu.sicnu.cs.stu.xuruizhi.first

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var tvHello: TextView
    private lateinit var ivFlag: ImageView
    // 0中国(zh) 1美国(en) 2韩国(ko)
    private var langIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 根布局 垂直排列
        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(40,80,40,40)
        root.gravity = android.view.Gravity.CENTER_HORIZONTAL

        // 国旗图片
        ivFlag = ImageView(this)
        ivFlag.layoutParams = LinearLayout.LayoutParams(420, 280)
        root.addView(ivFlag)

        // 文字Hello
        tvHello = TextView(this)
        tvHello.textSize = 30f
        tvHello.setPadding(0,40,0,40)
        root.addView(tvHello)

        // 切换按钮
        val btnChange = Button(this)
        btnChange.text = "切换语言"
        btnChange.textSize = 22f
        btnChange.setOnClickListener {
            switchLang()
        }
        root.addView(btnChange)

        setContentView(root)
        updateUI()
    }

    // 切换语言逻辑
    private fun switchLang(){
        langIndex = (langIndex + 1) % 3
        when(langIndex){
            0 -> setLocal("zh", "CN")
            1 -> setLocal("en", "US")
            2 -> setLocal("ko", "KR")
        }
        updateUI()
    }

    // 设置区域语言
    private fun setLocal(langCode:String, region:String){
        val locale = Locale(langCode, region)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    // 更新文字+国旗
    private fun updateUI(){
        tvHello.setText(R.string.hello)
        when(langIndex){
            0 -> ivFlag.setImageResource(R.drawable.cn)
            1 -> ivFlag.setImageResource(R.drawable.us)
            2 -> ivFlag.setImageResource(R.drawable.kr)
        }
    }
}