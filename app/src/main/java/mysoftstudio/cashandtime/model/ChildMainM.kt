package mysoftstudio.cashandtime.model

import android.util.Log
import androidx.collection.ArrayMap
import kotlinx.coroutines.*
import mysoftstudio.cashandtime.presenter.pi.ChildMainPI
import mysoftstudio.cashandtime.tool.AppNetwork
import kotlin.coroutines.CoroutineContext

class ChildMainM(private val pi: ChildMainPI) : CoroutineScope {
    private val ERROR_MSG = "ChildMainM"
    private val job by lazy { Job() }
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Log.e(ERROR_MSG, throwable.toString()) }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    fun getData(map: ArrayMap<String, String>) {
        launch {
            val cash = async(Dispatchers.IO) { AppNetwork.network.getCashData(map) }
            val time = async(Dispatchers.IO) { AppNetwork.network.getTimeData(map) }
            pi.handleResultData(cash.await(), time.await())
        }
    }
}