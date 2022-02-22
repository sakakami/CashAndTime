package mysoftstudio.cashandtime.model

import android.util.Log
import androidx.collection.ArrayMap
import kotlinx.coroutines.*
import mysoftstudio.cashandtime.presenter.pi.HomePI
import mysoftstudio.cashandtime.tool.AppNetwork
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.coroutines.CoroutineContext

/**
 * 使用coroutine來進行異步處理
 */

class HomeM(private val pi: HomePI) : CoroutineScope {
    private val ERROR_MSG = "HomeM"
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Log.e(ERROR_MSG, "error -> $throwable") }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job() + exceptionHandler

    fun sendCreateData(map: ArrayMap<String, String>) {
        launch {
            val userData = async(Dispatchers.IO) { AppNetwork.network.sendMemberData(map) }
            pi.handleFinishCreate(userData.await())
        }
    }

    fun getUserData(map: ArrayMap<String, String>) {
        launch {
            val userData = async(Dispatchers.IO) { AppNetwork.network.getMemberData(map) }
            pi.handleMemberData(userData.await())
        }
    }

    fun sendCreateDataHttp(map: ArrayMap<String, String>) {
        //Log.e(ERROR_MSG, "map -> $map")
        /*launch {
            val result = async(Dispatchers.IO) {
                val url = URL("http://mysoftstudio.link/SaveMember.php")
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connectTimeout = 30000
                val builder = StringBuilder("data=")
                builder.append(URLEncoder.encode(map["data"], "UTF-8"))
                builder.append("&check=")
                builder.append(URLEncoder.encode(map["check"], "UTF-8"))
                val outputStream = DataOutputStream(httpURLConnection.outputStream)
                outputStream.writeBytes(builder.toString())
                outputStream.flush()
                outputStream.close()
                val inputStream = httpURLConnection.inputStream
                val reader = InputStreamReader(inputStream, "UTF-8")
                BufferedReader(reader).readLine()
            }
            Log.e(ERROR_MSG, "result -> ${result.await()}")
        }*/
    }
}