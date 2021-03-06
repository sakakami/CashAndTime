package mysoftstudio.cashandtime.presenter

import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.collection.ArrayMap
import com.google.gson.Gson
import mysoftstudio.cashandtime.MyApplication
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.UserIdData
import mysoftstudio.cashandtime.gson.Cash2G
import mysoftstudio.cashandtime.gson.CashG
import mysoftstudio.cashandtime.gson.Time2G
import mysoftstudio.cashandtime.gson.TimeG
import mysoftstudio.cashandtime.model.ChildMainM
import mysoftstudio.cashandtime.presenter.pi.ChildMainPI
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.ChildMainVI

class ChildMainP(private val vi: ChildMainVI) : ChildMainPI {
    private val m by lazy { ChildMainM(this) }
    private var mCashG = Cash2G()
    private var mTimeG = Time2G()
    private var mUserId by Preferences("userId", "")
    private var mUserName by Preferences("userName", "")
    private var check by Preferences("isChecked", false)

    fun getData() {
        val userIdList = ArrayList<UserIdData>()
        val userIdData = UserIdData()
        userIdData.userId = mUserId
        userIdList.add(userIdData)
        val json = Gson().toJson(userIdList)
        val map = ArrayMap<String, String>()
        map["name"] = json
        map["check"] = MyApplication.instance.handleShaEncode("name=$json${MyApplication.checkKey}")
        m.getData(map)
    }

    fun initDayNightMode() {
        AppCompatDelegate.setDefaultNightMode(if (check) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun handleClickCash() { vi.toCashPage(mCashG) }

    fun handleClickTime() { vi.toTimePage(mTimeG) }

    fun getMemberInfo() {
        val info = "暱稱：$mUserName\n會員編號：$mUserId\n會員編號建議截圖保存，會員編號是取回資料所需必須資料。"
        vi.showMessage(info)
    }

    fun handleMenuClick(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_item_night -> {
                switchDayNightMode()
                true
            }
            R.id.menu_item_about -> {
                vi.showAbout()
                true
            }
            else -> false
        }
    }

    override fun handleResultData(cashG: CashG, timeG: TimeG) {
        mCashG = cashG.data[0]
        mTimeG = timeG.data[0]
        val showCash = cashG.data[0].cashData[0].cashTotal + " NTD"
        var showTime = "00:00"
        val timeTotal = timeG.data[0].timeData[0].timeTotal.toInt()
        showTime = if (timeTotal < 0) {
            val builder = StringBuilder(timeTotal.toString())
            builder.delete(0, 1)
            val hour = builder.toString().toInt() / 60
            val min = builder.toString().toInt() % 60
            val textHour = if (hour > 9) hour.toString() else "0$hour"
            val textMin = if (min > 9) min.toString() else "0$min"
            "-$textHour:$textMin"
        } else {
            val hour = timeTotal / 60
            val finalMin = timeTotal % 60
            val hourToString = if (hour > 9) "$hour" else "0$hour"
            val finalToString = if (finalMin > 9) "$finalMin" else "0$finalMin"
            "$hourToString:$finalToString"
        }
        vi.refreshData(showCash, showTime)
    }

    private fun switchDayNightMode() {
        if (check) {
            check = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            check = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}