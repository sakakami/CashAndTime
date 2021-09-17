package mysoftstudio.cashandtime.presenter

import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.CashData
import mysoftstudio.cashandtime.databinding.HolderItemBinding
import mysoftstudio.cashandtime.view.vi.CashInfoListVI

class CashInfoListP(private val vi: CashInfoListVI) {
    private var cashList = ArrayList<CashData>()

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
    }
}