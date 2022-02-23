package mysoftstudio.cashandtime.presenter

import androidx.collection.ArrayMap
import com.google.gson.Gson
import mysoftstudio.cashandtime.MyApplication
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.CashData
import mysoftstudio.cashandtime.data.UserIdData
import mysoftstudio.cashandtime.databinding.HolderItemBinding
import mysoftstudio.cashandtime.gson.CashG
import mysoftstudio.cashandtime.gson.DefaultG
import mysoftstudio.cashandtime.model.CashInfoListM
import mysoftstudio.cashandtime.presenter.pi.CashInfoListPI
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.CashInfoListVI

class CashInfoListP(private val vi: CashInfoListVI) : CashInfoListPI {
    private var cashList = ArrayList<CashData>()
    private val m by lazy { CashInfoListM(this) }
    private var userType by Preferences("userType", false)

    fun init(data: ArrayList<CashData>) {
        cashList = data
        vi.refreshAdapter(cashList.size)
    }

    fun handleHolder(holder: HolderItemBinding, position: Int) {
        holder.textDate.text = cashList[position].cashDate
        holder.textCreator.text = cashList[position].cashCreator
        holder.textInfo.text = cashList[position].cashInfo
        holder.textTotal.text = cashList[position].cashTotal
        val cashIn = cashList[position].cashIn.toInt()
        val cashOut = cashList[position].cashOut.toInt()
        if (cashIn == 0) {
            holder.viewBackground.setBackgroundResource(R.drawable.bg_surround_green)
            holder.textData.text = cashOut.toString()
        } else {
            holder.viewBackground.setBackgroundResource(R.drawable.bg_surround_red)
            holder.textData.text = cashIn.toString()
        }
        holder.viewBackground.setOnLongClickListener {
            if (position == 0 && userType) vi.delConfirm()
            true
        }
    }

    fun handleDelData() {
        val map = ArrayMap<String, String>()
        map["cashId"] = cashList[0].cashId
        map["check"] = MyApplication.instance.handleShaEncode("cashId=${cashList[0].cashId}${MyApplication.checkKey}")
        m.sendDelCashData(map)
    }

    override fun finishDelData(defaultG: DefaultG) {
        if (defaultG.result == "1") {
            val userIdList = ArrayList<UserIdData>()
            val userIdData = UserIdData()
            userIdData.userId = cashList[0].childId
            userIdList.add(userIdData)
            val gson = Gson().toJson(userIdList)
            val check = MyApplication.instance.handleShaEncode("name=$gson${MyApplication.checkKey}")
            val map = ArrayMap<String, String>()
            map["name"] = gson
            map["check"] = check
            m.getCashData(map)
            vi.showMsg(MyApplication.instance.getString(R.string.msg_cash_del_success))
        } else vi.showMsg(MyApplication.instance.getString(R.string.msg_cash_del_fail))
    }

    override fun handleCashData(cashG: CashG) {
        cashList = cashG.data[0].cashData
        vi.refreshAdapter(cashList.size)
    }
}