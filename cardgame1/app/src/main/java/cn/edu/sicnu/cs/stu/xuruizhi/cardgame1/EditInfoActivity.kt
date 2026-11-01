package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class EditInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)

        val etName:EditText = findViewById(R.id.et_name)
        val etStuId:EditText = findViewById(R.id.et_stuid)
        val etPhone:EditText = findViewById(R.id.et_phone)
        val etEmail:EditText = findViewById(R.id.et_email)
        val rgGender:RadioGroup = findViewById(R.id.rg_gender)
        val cbTravel:CheckBox = findViewById(R.id.cb_travel)
        val cbRead:CheckBox = findViewById(R.id.cb_read)
        val cbGame:CheckBox = findViewById(R.id.cb_game)
        val cbShop:CheckBox = findViewById(R.id.cb_shop)

        etName.setText(SPUtil.getString(this, SPUtil.KEY_NAME))
        etStuId.setText(SPUtil.getString(this, SPUtil.KEY_STU_ID))
        etPhone.setText(SPUtil.getString(this, SPUtil.KEY_PHONE))
        etEmail.setText(SPUtil.getString(this, SPUtil.KEY_EMAIL))

        val maleText = getString(R.string.text_male)
        val femaleText = getString(R.string.text_female)
        val oldGender = SPUtil.getString(this, SPUtil.KEY_GENDER)
        if (oldGender == maleText) findViewById<RadioButton>(R.id.rb_male).isChecked = true
        if (oldGender == femaleText) findViewById<RadioButton>(R.id.rb_female).isChecked = true

        val travelText = getString(R.string.hobby_travel)
        val readText = getString(R.string.hobby_read)
        val gameText = getString(R.string.hobby_game)
        val shopText = getString(R.string.hobby_shop)
        val oldHobby = SPUtil.getString(this, SPUtil.KEY_HOBBY)
        cbTravel.isChecked = oldHobby.contains(travelText)
        cbRead.isChecked = oldHobby.contains(readText)
        cbGame.isChecked = oldHobby.contains(gameText)
        cbShop.isChecked = oldHobby.contains(shopText)

        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.btn_save).setOnClickListener {
            SPUtil.putString(this, SPUtil.KEY_NAME, etName.text.toString())
            SPUtil.putString(this, SPUtil.KEY_STU_ID, etStuId.text.toString())
            SPUtil.putString(this, SPUtil.KEY_PHONE, etPhone.text.toString())
            SPUtil.putString(this, SPUtil.KEY_EMAIL, etEmail.text.toString())

            val genderId = rgGender.checkedRadioButtonId
            val genderText = when(genderId){
                R.id.rb_male -> maleText
                R.id.rb_female -> femaleText
                else -> ""
            }
            SPUtil.putString(this, SPUtil.KEY_GENDER, genderText)

            val hobbySb = StringBuilder()
            if (cbTravel.isChecked) { hobbySb.append(travelText).append(" ") }
            if (cbRead.isChecked) { hobbySb.append(readText).append(" ") }
            if (cbGame.isChecked) { hobbySb.append(gameText).append(" ") }
            if (cbShop.isChecked) { hobbySb.append(shopText).append(" ") }
            SPUtil.putString(this, SPUtil.KEY_HOBBY, hobbySb.toString().trim())

            Toast.makeText(this, getString(R.string.toast_save_success), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
