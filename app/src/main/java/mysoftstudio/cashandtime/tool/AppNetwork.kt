package mysoftstudio.cashandtime.tool

import android.util.Log
import androidx.annotation.Keep
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppNetwork {
    private val logging by lazy { HttpLoggingInterceptor{ Log.e("AppNetwork -> ", it) } }
    private val client by lazy {
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(logging)
            .build()
    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://mysoftstudio.link/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val network = retrofit.create(AppNetworkI::class.java)
}