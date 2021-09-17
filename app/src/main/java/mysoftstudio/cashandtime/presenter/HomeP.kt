package mysoftstudio.cashandtime.presenter

import android.util.Log
import androidx.collection.ArrayMap
import com.google.gson.Gson
import mysoftstudio.cashandtime.MyApplication
import mysoftstudio.cashandtime.R
import mysoftstudio.cashandtime.data.UserData
import mysoftstudio.cashandtime.gson.CreateMemberG
import mysoftstudio.cashandtime.gson.MemberG
import mysoftstudio.cashandtime.model.HomeM
import mysoftstudio.cashandtime.presenter.pi.HomePI
import mysoftstudio.cashandtime.tool.Preferences
import mysoftstudio.cashandtime.view.vi.HomeVI

class HomeP(private val vi: HomeVI) : HomePI {
    private val ERROR_MSG = "HomeP"
    private var userType by Preferences("userType", false)
    private var userId by Preferences("userId", "")
    private var userName by Preferences("userName", "")
    private var userChildA by Preferences("childA", "")
    private var userChildB by Preferences("childB", "")
    private var userChildC by Preferences("childC", "")
    private var userChildD by Preferences("childD", "")
    private var userChildE by Preferences("childE", "")
    private var userChildF by Preferences("childF", "")
    private var userChildG by Preferences("childG", "")
    private val m by lazy { HomeM(this) }

    fun handleSendData(isCreate: Boolean, name: String, isParent: Boolean) {
        if (name.isEmpty()) vi.showMessage("暱稱不可空白") else {
            val map = ArrayMap<String, String>()
            if (isCreate) {
                val list = ArrayList<UserData>()
                val userData = UserData()
                with(userData) {
                    userType = if (isParent) "parent" else "child"
                    userName = name
                }
                list.add(userData)
                val json = Gson().toJson(list)
                map["data"] = json
                map["check"] = MyApplication.instance.handleShaEncode("data=$json${MyApplication.checkKey}")
                m.sendCreateData(map)
                //m.sendCreateDataHttp(map)
            } else {
                map["userId"] = name
                map["check"] = MyApplication.instance.handleShaEncode("userId=$name${MyApplication.checkKey}")
                m.getUserData(map)
            }
        }
    }

    fun checkData() { if (userId.isNotEmpty()) { if (userType) vi.toCreatorPage() else vi.toChildPage() } }

    override fun handleFinishCreate(createMemberG: CreateMemberG) {
        if (createMemberG.result == "1") {
            userId = createMemberG.data[0].userId
            userType = createMemberG.data[0].userType == "parent"
            userName = createMemberG.data[0].userName
            if (createMemberG.data[0].userType == "parent") vi.toCreatorPage()
            else vi.toChildPage()
        } else vi.showMessage(MyApplication.instance.getString(R.string.home_create_fail))
    }

    override fun handleMemberData(memberG: MemberG) {
        if (memberG.data.isEmpty()) vi.showMessage(MyApplication.instance.getString(R.string.home_inherit_fail)) else {
            userId = memberG.data[0].userId
            userName = memberG.data[0].userName
            userType = memberG.data[0].userType == "parent"
            userChildA = memberG.data[0].userChildA
            userChildB = memberG.data[0].userChildB
            userChildC = memberG.data[0].userChildC
            userChildD = memberG.data[0].userChildD
            userChildE = memberG.data[0].userChildE
            userChildF = memberG.data[0].userChildF
            userChildG = memberG.data[0].userChildG
            Log.e(ERROR_MSG, "id -> ${memberG.data[0].userId}, name -> ${memberG.data[0].userName}, type -> ${memberG.data[0].userType}")
            if (memberG.data[0].userType == "parent") vi.toCreatorPage()
            else vi.toChildPage()
        }
    }
}