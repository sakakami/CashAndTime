package mysoftstudio.cashandtime.model

import android.util.Log
import androidx.collection.ArrayMap
import kotlinx.coroutines.*
import mysoftstudio.cashandtime.presenter.pi.CreatorMainPI
import mysoftstudio.cashandtime.tool.AppNetwork
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext

class CreatorMainM(private val pi: CreatorMainPI) : CoroutineScope {
    private val ERROR_MSG = "CreatorMainM"
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

    fun sendCashData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.sendCashData(map) }
            pi.finishSendCashData(result.await())
        }
    }

    fun sendTimeData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.sendTimeData(map) }
            pi.finishSendTimeData(result.await())
        }
    }

    fun sendChildData(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) { AppNetwork.network.sendChildData(map) }
            pi.finishSendChildData(result.await())
        }
    }

    fun getUserData(map: ArrayMap<String, String>) {
        launch {
            val userData = async(Dispatchers.IO) { AppNetwork.network.getMemberData(map) }
            pi.handleMemberData(userData.await())
        }
    }

    fun getUserDataWithHttp(map: ArrayMap<String, String>) {
        launch {
            val result = async(Dispatchers.IO) {
                val param = "name=${map["name"]}&check=${map["check"]}"
                val url = URL("http://mysoftstudio.link/GetCash.php?$param")
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "GET"
                httpURLConnection.connectTimeout = 30000
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)
                inputStream.close()
                bufferedReader.readLine()
            }
            Log.e(ERROR_MSG, "data -> ${result.await()}")
        }
    }
}