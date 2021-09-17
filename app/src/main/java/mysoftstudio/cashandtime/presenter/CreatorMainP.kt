package mysoftstudio.cashandtime.presenter

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.collection.ArrayMap
import com.bumptech.glide.Glide
import com.google.gson.Gson
import mysoftstudio.cashandtime.MyApplication
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.CashData
import mysoftstudio.cashandtime.data.TimeData
import mysoftstudio.cashandtime.data.UserIdData
import mysoftstudio.cashandtime.databinding.HolderWalletBinding
import mysoftstudio.cashandtime.gson.*
import mysoftstudio.cashandtime.model.CreatorMainM
import mysoftstudio.cashandtime.presenter.pi.CreatorMainPI
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.CreatorMainVI
import java.util.*
import kotlin.collections.ArrayList

class CreatorMainP(private val vi: CreatorMainVI) : CreatorMainPI {
    private val ERROR_MSG = "CreatorMainP"
    private val mSimpleDateFormat = SimpleDateFormat("yyyy/M/d", Locale.TAIWAN)
    private val m by lazy { CreatorMainM(this) }
    private var mUserId by Preferences("userId", "")
    private var mUserName by Preferences("userName", "")
    private var mUserType by Preferences("userType", false)
    private var mChildA by Preferences("childA", "")
    private var mChildB by Preferences("childB", "")
    private var mChildC by Preferences("childC", "")
    private var mChildD by Preferences("childD", "")
    private var mChildE by Preferences("childE", "")
    private var mChildF by Preferences("childF", "")
    private var mChildG by Preferences("childG", "")
    private var mCashList = ArrayList<Cash2G>()
    private var mTimeList = ArrayList<Time2G>()

    fun getData() {
        if (mChildA.isNotEmpty()) {
            val userIdList = ArrayList<UserIdData>()
            val userIdData = UserIdData()
            userIdData.userId = mChildA
            userIdList.add(userIdData)
            if (mChildB.isNotEmpty()) {
                val userIdDataB = UserIdData()
                userIdDataB.userId = mChildB
                userIdList.add(userIdDataB)
            }
            if (mChildC.isNotEmpty()) {
                val userIdDataC = UserIdData()
                userIdDataC.userId = mChildC
                userIdList.add(userIdDataC)
            }
            if (mChildD.isNotEmpty()) {
                val userIdDataD = UserIdData()
                userIdDataD.userId = mChildD
                userIdList.add(userIdDataD)
            }
            if (mChildE.isNotEmpty()) {
                val userIdDataE = UserIdData()
                userIdDataE.userId = mChildE
                userIdList.add(userIdDataE)
            }
            if (mChildF.isNotEmpty()) {
                val userIdDataF = UserIdData()
                userIdDataF.userId = mChildF
                userIdList.add(userIdDataF)
            }
            if (mChildG.isNotEmpty()) {
                val userIdDataG = UserIdData()
                userIdDataG.userId = mChildG
                userIdList.add(userIdDataG)
            }
            val json = Gson().toJson(userIdList)
            val check = MyApplication.instance.handleShaEncode("name=$json${MyApplication.checkKey}")
            val map = ArrayMap<String, String>()
            map["name"] = json
            map["check"] = check
            /*map.forEach { s, s2 ->
                Log.e(ERROR_MSG, "key -> $s, value -> $s2")
            }*/
            m.getData(map)
            //m.getUserDataWithHttp(map)
        } else vi.showAddMessage()
    }

    fun handleWalletHolder(holder: HolderWalletBinding, position: Int) {
        Glide.with(MyApplication.instance)
            .asGif()
            .load(R.drawable.diamond)
            .into(holder.imageCash)
        Glide.with(MyApplication.instance)
            .asGif()
            .load(R.drawable.clock)
            .into(holder.imageTime)
        holder.textChild.text = mCashList[position].cashData[0].childName
        holder.textCash.text = mCashList[position].cashData[0].cashTotal
        val timeTotal = mTimeList[position].timeData[0].timeTotal.toInt()
        val hour = timeTotal / 60
        val min = timeTotal % 60
        val textHour = if (hour > 9) hour.toString() else "0$hour"
        val textMin = if (min > 9) min.toString() else "0$min"
        val textTime = "$textHour:$textMin"
        holder.textTime.text = textTime
    }

    fun handleShowCash(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("cash", mCashList[position].cashData)
        vi.toShowCashPage(bundle)
    }

    fun handleShowTime(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("time", mTimeList[position].timeData)
        vi.toShowTimePage(bundle)
    }

    fun getChildNameForCash(position: Int) {
        val name = mCashList[position].cashData[0].childName
        vi.showAddCash(name, position)
    }

    fun getChildNameForTime(position: Int) {
        val name = mTimeList[position].timeData[0].childName
        vi.showAddTime(name, position)
    }

    fun handleAddCash(checkA: Boolean, cash: String, info: String, position: Int) {
        val total = mCashList[position].cashData[0].cashTotal.toInt()
        val finalTotal = if (checkA) total + cash.toInt() else total - cash.toInt()
        val list = ArrayList<CashData>()
        val cashData = CashData()
        with(cashData) {
            cashDate = mSimpleDateFormat.format(Date())
            childId = mCashList[position].cashData[0].childId
            childName = mCashList[position].cashData[0].childName
            cashIn = if (checkA) cash else "0"
            cashOut = if (checkA) "0" else cash
            cashTotal = finalTotal.toString()
            cashCreator = mUserName
            cashInfo = info
        }
        list.add(cashData)
        val json = Gson().toJson(list)
        val map = ArrayMap<String, String>()
        map["data"] = json
        map["check"] = MyApplication.instance.handleShaEncode("data=$json${MyApplication.checkKey}")
        Log.e(ERROR_MSG, "json -> $json")
        m.sendCashData(map)
    }

    fun handleAddTime(checkA: Boolean, time: String, info: String, position: Int) {
        val total = mTimeList[position].timeData[0].timeTotal.toInt()
        val finalTotal = if (checkA) total + time.toInt() else total - time.toInt()
        val list = ArrayList<TimeData>()
        val timeData = TimeData()
        with(timeData) {
            timeDate = mSimpleDateFormat.format(Date())
            childId = mTimeList[position].timeData[0].childId
            childName = mTimeList[position].timeData[0].childName
            timeIn = if (checkA) time else "0"
            timeOut = if (checkA) "0" else time
            timeTotal = finalTotal.toString()
            timeCreator = mUserName
            timeInfo = info
        }
        list.add(timeData)
        val json = Gson().toJson(list)
        val map = ArrayMap<String, String>()
        map["data"] = json
        map["check"] = MyApplication.instance.handleShaEncode("data=$json${MyApplication.checkKey}")
        m.sendTimeData(map)
    }

    fun handleAddWallet(childId: String) {
        val map = ArrayMap<String, String>()
        map["userId"] = mUserId
        map["childId"] = childId
        map["check"] = MyApplication.instance.handleShaEncode("userId=$mUserId&childId=$childId${MyApplication.checkKey}")
        m.sendChildData(map)
    }

    fun getMemberInfo() {
        val info = "暱稱：$mUserName\n會員編號：$mUserId\n會員編號建議截圖保存，此編號是找回資料的必須資訊。"
        vi.showMessage(info)
    }

    override fun handleResultData(cash: CashG, time: TimeG) {
        mCashList = cash.data
        mTimeList = time.data
        vi.refreshAdapter(mCashList.size)
    }

    override fun finishSendCashData(defaultG: DefaultG) {
        if (defaultG.result == "1") {
            vi.showMessage(MyApplication.instance.getString(R.string.msg_cash_success))
            getData()
        } else vi.showMessage(MyApplication.instance.getString(R.string.msg_cash_fail))
    }

    override fun finishSendTimeData(defaultG: DefaultG) {
        if (defaultG.result == "1") {
            vi.showMessage(MyApplication.instance.getString(R.string.msg_time_success))
            getData()
        } else vi.showMessage(MyApplication.instance.getString(R.string.msg_time_fail))
    }

    override fun finishSendChildData(defaultG: DefaultG) {
        when (defaultG.result) {
            "0" -> vi.showMessage(MyApplication.instance.getString(R.string.creator_msg_fail))
            "2" -> vi.showMessage(MyApplication.instance.getString(R.string.creator_msg_full))
            else -> {
                vi.showMessage(MyApplication.instance.getString(R.string.creator_msg_success))
                handleGetUserInfo()
            }
        }
    }

    override fun handleMemberData(memberG: MemberG) {
        if (memberG.data.isNotEmpty()) {
            mUserId = memberG.data[0].userId
            mUserName = memberG.data[0].userName
            mUserType = memberG.data[0].userType == "parent"
            mChildA = memberG.data[0].userChildA
            mChildB = memberG.data[0].userChildB
            mChildC = memberG.data[0].userChildC
            mChildD = memberG.data[0].userChildD
            mChildE = memberG.data[0].userChildE
            mChildF = memberG.data[0].userChildF
            mChildG = memberG.data[0].userChildG
            getData()
        }
    }

    private fun handleGetUserInfo() {
        val map = ArrayMap<String, String>()
        map["userId"] = mUserId
        map["check"] = MyApplication.instance.handleShaEncode("userId=$mUserId${MyApplication.checkKey}")
        m.getUserData(map)
    }
}