package mysoftstudio.cashandtime.presenter.pi

import mysoftstudio.cashandtime.gson.CashG
import mysoftstudio.cashandtime.gson.TimeG

interface ChildMainPI {
    fun handleResultData(cashG: CashG, timeG: TimeG)
}