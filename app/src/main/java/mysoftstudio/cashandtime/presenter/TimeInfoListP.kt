package mysoftstudio.cashandtime.presenter

import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.TimeData
import mysoftstudio.cashandtime.databinding.HolderItemBinding
import mysoftstudio.cashandtime.view.vi.TimeInfoListVI

class TimeInfoListP(private val vi: TimeInfoListVI) {
    private var timeList = ArrayList<TimeData>()

    fun init(data: ArrayList<TimeData>) {
        timeList = data
        vi.refreshAdapter(timeList.size)
    }

    fun handleHolder(holder: HolderItemBinding, position: Int) {
        holder.textDate.text = timeList[position].timeDate
        holder.textCreator.text = timeList[position].timeCreator
        holder.textInfo.text = timeList[position].timeInfo
        val total = timeList[position].timeTotal.toInt()
        if (total > 59) {
            val hour = total / 60
            val min = total % 60
            val txtHour = if (hour > 9) hour.toString() else "0$hour"
            val txtMin = if (min > 9) min.toString() else "0$min"
            val txtTime = "$txtHour:$txtMin"
            holder.textTotal.text = txtTime
        } else {
            val txtTime = if (total > 9) "00:$total" else "00:0$total"
            holder.textTotal.text = txtTime
        }
        val timeIn = timeList[position].timeIn.toInt()
        val timeOut = timeList[position].timeOut.toInt()
        if (timeIn == 0) {
            holder.viewBackground.setBackgroundResource(R.drawable.bg_surround_green)
            if (timeOut > 59) {
                val hour = timeOut / 60
                val min = timeOut % 60
                val txtHour = if (hour > 9) hour.toString() else "0$hour"
                val txtMin = if (min > 9) min.toString() else "0$min"
                val txtTime = "$txtHour:$txtMin"
                holder.textData.text = txtTime
            } else {
                val txtTime = if (timeOut > 9) "00:$timeOut" else "00:0$timeOut"
                holder.textData.text = txtTime
            }
        } else {
            holder.viewBackground.setBackgroundResource(R.drawable.bg_surround_red)
            if (timeIn > 59) {
                val hour = timeIn / 60
                val min = timeIn % 60
                val txtHour = if (hour > 9) hour.toString() else "0$hour"
                val txtMin = if (min > 9) min.toString() else "0$min"
                val txtTime = "$txtHour:$txtMin"
                holder.textData.text = txtTime
            } else {
                val txtTime = if (timeIn > 9) "00:$timeIn" else "00:0$timeIn"
                holder.textData.text = txtTime
            }
        }
    }
}