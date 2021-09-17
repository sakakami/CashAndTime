package mysoftstudio.cashandtime

import android.app.Application
import java.security.MessageDigest
import kotlin.properties.Delegates

class MyApplication: Application() {
    companion object {
        var instance: MyApplication by Delegates.notNull()
        const val checkKey = "54957f83ba7c8991416"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun handleShaEncode(string: String): String = MessageDigest.getInstance("SHA-256").digest(string.toByteArray()).joinToString(separator = "") { "%02x".format(it) }
}