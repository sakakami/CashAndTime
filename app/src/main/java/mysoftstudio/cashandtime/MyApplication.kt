package mysoftstudio.cashandtime

import android.app.Application
import java.security.MessageDigest
import kotlin.properties.Delegates

/**
 * Application的實例，在部分無法取得context的地方可以透過此實例取得context
 * 與伺服器交換資料所需驗證金鑰，以及sha-256加密功能，放在Application實例可以方便隨時取得
 */

class MyApplication: Application() {
    companion object {
        var instance: MyApplication by Delegates.notNull()
        const val checkKey = ""
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun handleShaEncode(string: String): String = MessageDigest.getInstance("SHA-256").digest(string.toByteArray()).joinToString(separator = "") { "%02x".format(it) }
}