package mysoftstudio.cashandtime.view.vi

import mysoftstudio.cashandtime.gson.Cash2G
import mysoftstudio.cashandtime.gson.Time2G

interface ChildMainVI {
    fun refreshData(cash: String, time: String)
    fun toCashPage(cashG: Cash2G)
    fun toTimePage(timeG: Time2G)
    fun showMessage(msg: String)
    fun showAbout()
}