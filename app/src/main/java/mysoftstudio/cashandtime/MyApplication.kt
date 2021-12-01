package mysoftstudio.cashandtime

import android.app.Application
import java.security.MessageDigest
import kotlin.properties.Delegates

class MyApplication: Application() {
    companion object {
        var instance: MyApplication by Delegates.notNull()
        const val checkKey = "54957f83ba7c899141633f96cb2bf948639430a777853c1f2288c8c73765d3fe"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun handleShaEncode(string: String): String = MessageDigest.getInstance("SHA-256").digest(string.toByteArray()).joinToString(separator = "") { "%02x".format(it) }
}