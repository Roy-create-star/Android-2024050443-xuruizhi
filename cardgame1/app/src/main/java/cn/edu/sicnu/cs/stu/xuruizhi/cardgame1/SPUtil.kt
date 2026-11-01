package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1
import android.content.Context
import android.content.SharedPreferences
object SPUtil {
    private const val SP_NAME = "card_game_sp"
    const val KEY_NAME = "name"
    const val KEY_STU_ID = "stuId"
    const val KEY_GENDER = "gender"
    const val KEY_PHONE = "phone"
    const val KEY_EMAIL = "email"
    const val KEY_HOBBY = "hobby"
    const val KEY_HIGH_SCORE = "high_score"

    fun getSp(context: Context): SharedPreferences{
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }
    fun putString(context:Context,key:String,value:String){
        getSp(context).edit().putString(key,value).apply()
    }
    fun getString(context:Context,key:String,def:String=""):String{
        return getSp(context).getString(key,def)!!
    }
    fun putInt(context:Context,key:String,value:Int){
        getSp(context).edit().putInt(key,value).apply()
    }
    fun getInt(context:Context,key:String,def:Int=0):Int{
        return getSp(context).getInt(key,def)
    }
}
