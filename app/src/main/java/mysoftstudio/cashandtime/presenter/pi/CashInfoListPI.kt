package mysoftstudio.cashandtime.presenter.pi

import mysoftstudio.cashandtime.gson.CashG
import mysoftstudio.cashandtime.gson.DefaultG

interface CashInfoListPI {
    fun finishDelData(defaultG: DefaultG)
    fun handleCashData(cashG: CashG)
}