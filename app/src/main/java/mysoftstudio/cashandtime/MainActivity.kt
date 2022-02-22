package mysoftstudio.cashandtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * main activity只在activity_main.xml上建立FragmentContentView並連接Navigation
 * 建立Navigation功能所需環境，在res建立navigation資料夾並建立nav_graph.xml
 * 詳細設定請參照activity_main.xml和nav_graph.xml
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}