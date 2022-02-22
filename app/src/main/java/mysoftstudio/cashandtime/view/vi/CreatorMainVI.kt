package mysoftstudio.cashandtime.view.vi

import android.os.Bundle

interface CreatorMainVI {
    fun toShowCashPage(bundle: Bundle)
    fun toShowTimePage(bundle: Bundle)
    fun showMessage(msg: String)
    fun showAddCash(name: String, position: Int)
    fun showAddTime(name: String, position: Int)
    fun showAddMessage()
    fun refreshAdapter(size: Int)
    fun showAbout()
}