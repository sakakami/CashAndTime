package mysoftstudio.cashandtime.presenter

import androidx.collection.ArrayMap
import com.google.gson.Gson
import mysoftstudio.cashandtime.MyApplication
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.TimeData
import mysoftstudio.cashandtime.data.UserIdData
import mysoftstudio.cashandtime.databinding.HolderItemBinding
import mysoftstudio.cashandtime.gson.DefaultG
import mysoftstudio.cashandtime.gson.TimeG
import mysoftstudio.cashandtime.model.TimeInfoListM
import mysoftstudio.cashandtime.presenter.pi.TimeInfoListPI
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.TimeInfoListVI

class TimeInfoListP(private val vi: TimeInfoListVI) : TimeInfoListPI {
    private var timeList = ArrayList<TimeData>()
    private val m by lazy { TimeInfoListM(this) }
    private var userType by Preferences("userType", false)

    fun init(data: ArrayList<TimeData>) {
        timeList = data
        vi.refreshAdapter(timeList.size)
    }

    fun handleHolder(holder: HolderItemBinding, position: Int) {
        holder.textDate.text = timeList[position].timeDate
        holder.textCreator.text = timeList[position].timeCreator
        holder.textInfo.text = timeList[position].timeInfo
        val total = timeList[position].timeTotal.toInt()
        if (total < 0) {
            val builder = StringBuilder(total.toString())
            builder.delete(0, 1)
            val hour = builder.toString().toInt() / 60
            val min = builder.toString().toInt() % 60
            val txtHour = if (hour > 9) hour.toString() else "0$hour"
            val txtMin = if (min > 9) min.toString() else "0$min"
            val txtTime = "-$txtHour:$txtMin"
            holder.textTotal.text = txtTime
        } else {
            val hour = total / 60
            val min = total % 60
            val txtHour = if (hour > 9) hour.toString() else "0$hour"
            val txtMin = if (min > 9) min.toString() else "0$min"
            val txtTime = "$txtHour:$txtMin"
            holder.textTotal.text = txtTime
        }
        val timeIn = timeList[position].timeIn.toInt()
        val timeOut = timeList[position].timeOut.toInt()
        if (timeIn == 0) {
            holder.viewBackground.setBackgroundResource(R.drawable.bg_surround_green)
            val hour = timeOut / 60
            val min = timeOut % 60
            val txtHour = if (hour > 9) hour.toString() else "0$hour"
            val txtMin = if (min > 9) min.toString() else "0$min"
            val txtTime = "$txtHour:$txtMin"
            holder.textData.text = txtTime
        } else {
            holder.viewBackground.setBackgroundResource(R.drawable.bg_surround_red)
            val hour = timeIn / 60
            val min = timeIn % 60
            val txtHour = if (hour > 9) hour.toString() else "0$hour"
            val txtMin = if (min > 9) min.toString() else "0$min"
            val txtTime = "$txtHour:$txtMin"
            holder.textData.text = txtTime
        }

        holder.viewBackground.setOnLongClickListener {
            if (position == 0 && userType) vi.delConfirm()
            true
        }
    }

    fun handleDelData() {
        val map = ArrayMap<String, String>()
        map["timeId"] = timeList[0].timeId
        map["check"] = MyApplication.instance.handleShaEncode("timeId=${timeList[0].timeId}${MyApplication.checkKey}")
        m.sendDelTimeData(map)
    }

    override fun finishDelData(defaultG: DefaultG) {
        if (defaultG.result == "1") {
            val userIdList = ArrayList<UserIdData>()
            val userIdData = UserIdData()
            userIdData.userId = timeList[0].childId
            userIdList.add(userIdData)
            val gson = Gson().toJson(userIdList)
            val check = MyApplication.instance.handleShaEncode("name=$gson${MyApplication.checkKey}")
            val map = ArrayMap<String, String>()
            map["name"] = gson
            map["check"] = check
            m.getTimeData(map)
            vi.showMsg(MyApplication.instance.getString(R.string.msg_time_del_success))
        } else vi.showMsg(MyApplication.instance.getString(R.string.msg_time_del_fail))
    }

    override fun handleTimeData(timeG: TimeG) {
        timeList = timeG.data[0].timeData
        vi.refreshAdapter(timeList.size)
    }
}