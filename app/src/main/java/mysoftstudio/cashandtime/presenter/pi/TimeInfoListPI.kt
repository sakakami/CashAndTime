package mysoftstudio.cashandtime.presenter.pi

import mysoftstudio.cashandtime.gson.DefaultG
import mysoftstudio.cashandtime.gson.TimeG

interface TimeInfoListPI {
    fun finishDelData(defaultG: DefaultG)
    fun handleTimeData(timeG: TimeG)
}