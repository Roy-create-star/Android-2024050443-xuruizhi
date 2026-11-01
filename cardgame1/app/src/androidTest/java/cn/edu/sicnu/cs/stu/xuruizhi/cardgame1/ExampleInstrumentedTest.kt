package cn.edu.sicnu.cs.stu.xuruizhi.cardgame1

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI 测试：验证 3 个 Activity 的显示与交互。
 */
@RunWith(AndroidJUnit4::class)
class UITest {

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("cn.edu.sicnu.cs.stu.xuruizhi.cardgame1", appContext.packageName)
    }

    /** 主 Activity 显示：应用标题、修改信息按钮、开始游戏按钮 */
    @Test
    fun mainActivity_displaysKeyViews() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btn_edit_info)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_start_game)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        }
    }

    /** 点击"进入翻牌游戏"按钮能启动 GameActivity */
    @Test
    fun mainActivity_clickStartGame_launchesGameActivity() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btn_start_game)).perform(click())
            onView(withId(R.id.gv_cards)).check(matches(isDisplayed()))
            onView(withId(R.id.tv_score)).check(matches(isDisplayed()))
        }
    }

    /** 点击"修改个人信息"按钮能启动 EditInfoActivity */
    @Test
    fun mainActivity_clickEditInfo_launchesEditInfoActivity() {
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.btn_edit_info)).perform(click())
            onView(withId(R.id.et_name)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_save)).check(matches(isDisplayed()))
            onView(withId(R.id.btn_cancel)).check(matches(isDisplayed()))
        }
    }

    /** EditInfoActivity 保存功能：输入姓名 → 保存 → 返回主页面显示 */
    @Test
    fun editInfoActivity_saveName_returnsToMain() {
        ActivityScenario.launch(EditInfoActivity::class.java).use {
            onView(withId(R.id.et_name)).perform(typeText("续睿智"), closeSoftKeyboard())
            onView(withId(R.id.et_stuid)).perform(typeText("2024050443"), closeSoftKeyboard())
            onView(withId(R.id.rb_male)).perform(click())
            onView(withId(R.id.cb_travel)).perform(click())
            onView(withId(R.id.btn_save)).perform(click())
        }
        // 保存后 finish 回到主页面，姓名应显示为"续睿智"
        ActivityScenario.launch(MainActivity::class.java).use {
            onView(withId(R.id.tv_name)).check(matches(withText("姓名:续睿智")))
        }
    }

    /** GameActivity 点击卡片：卡片按钮存在且可点击 */
    @Test
    fun gameActivity_clickCard_updatesUI() {
        ActivityScenario.launch(GameActivity::class.java).use {
            // 点击第一张牌（GridView 的第 0 项）
            onView(withId(R.id.gv_cards)).check(matches(isDisplayed()))
            // 点击重置按钮，游戏应正常刷新分数为 0
            onView(withId(R.id.btn_reset)).perform(click())
            onView(withId(R.id.tv_score)).check(matches(withText("得分:0")))
        }
    }
}
