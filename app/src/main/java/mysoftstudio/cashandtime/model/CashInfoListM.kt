package mysoftstudio.cashandtime.model

import android.util.Log
import androidx.collection.ArrayMap
import kotlinx.coroutines.*
import mysoftstudio.cashandtime.presenter.pi.CashInfoListPI
import mysoftstudio.cashandtime.tool.AppNetwork
import kotlin.coroutines.CoroutineContext

class CashInfoListM(private val pi: CashInfoListPI) : CoroutineScope {
    private val ERROR_MSG = "CashInfoListM"
    private val job by lazy { Job() }
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Log.e(ERROR_MSG, throwable.toString()) }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    fun sendDelCashData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.sendDelCashData(map) }
            pi.finishDelData(result.await())
        }
    }

    fun getCashData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.getCashData(map) }
            pi.handleCashData(result.await())
        }
    }
}