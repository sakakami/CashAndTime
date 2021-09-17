package mysoftstudio.cashandtime.presenter

import androidx.collection.ArrayMap
import com.google.gson.Gson
import mysoftstudio.cashandtime.MyApplication
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

    fun handleClickCash() { vi.toCashPage(mCashG) }

    fun handleClickTime() { vi.toTimePage(mTimeG) }

    fun getMemberInfo() {
        val info = "暱稱：$mUserName\n會員編號：$mUserId\n會員編號建議截圖保存，會員編號是取回資料所需必須資料。"
        vi.showMessage(info)
    }

    override fun handleResultData(cashG: CashG, timeG: TimeG) {
        mCashG = cashG.data[0]
        mTimeG = timeG.data[0]
        val showCash = cashG.data[0].cashData[0].cashTotal + " NTD"
        var showTime = "00:00"
        val min = timeG.data[0].timeData[0].timeTotal.toInt()
        showTime = if (min < 60) { if (min > 9) "00:$min" else "00:0$min" } else {
            val hour = min / 60
            val finalMin = min % 60
            val hourToString = if (hour > 9) "$hour" else "0$hour"
            val finalToString = if (finalMin > 9) "$finalMin" else "0$finalMin"
            "$hourToString:$finalToString"
        }
        vi.refreshData(showCash, showTime)
    }
}