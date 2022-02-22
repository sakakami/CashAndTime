package mysoftstudio.cashandtime.model

import android.util.Log
import androidx.collection.ArrayMap
import kotlinx.coroutines.*
import mysoftstudio.cashandtime.presenter.pi.TimeInfoListPI
import mysoftstudio.cashandtime.tool.AppNetwork
import kotlin.coroutines.CoroutineContext

class TimeInfoListM(private val pi: TimeInfoListPI) : CoroutineScope {
    private val ERROR_MSG = "TimeInfoListM"
    private val job by lazy { Job() }
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Log.e(ERROR_MSG, throwable.toString()) }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + exceptionHandler

    fun sendDelTimeData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.sendDelTimeData(map) }
            pi.finishDelData(result.await())
        }
    }

    fun getTimeData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.getTimeData(map) }
            pi.handleTimeData(result.await())
        }
    }
}