package mysoftstudio.cashandtime.presenter.pi

import mysoftstudio.cashandtime.gson.CashG
import mysoftstudio.cashandtime.gson.DefaultG
import mysoftstudio.cashandtime.gson.MemberG
import mysoftstudio.cashandtime.gson.TimeG

interface CreatorMainPI {
    fun handleResultData(cash: CashG, time: TimeG)
    fun finishSendCashData(defaultG: DefaultG)
    fun finishSendTimeData(defaultG: DefaultG)
    fun finishSendChildData(defaultG: DefaultG)
    fun handleMemberData(memberG: MemberG)
}