package mysoftstudio.cashandtime.tool

import androidx.collection.ArrayMap
import mysoftstudio.cashandtime.gson.*
import retrofit2.http.*

interface AppNetworkI {
    @GET("GetCash.php") suspend fun getCashData(@QueryMap map: ArrayMap<String, String>): CashG
    @GET("GetTime.php") suspend fun getTimeData(@QueryMap map: ArrayMap<String, String>): TimeG
    @GET("GetMember.php") suspend fun getMemberData(@QueryMap map: ArrayMap<String, String>): MemberG

    @FormUrlEncoded @POST("SaveCash.php") suspend fun sendCashData(@FieldMap map: ArrayMap<String, String>): DefaultG
    @FormUrlEncoded @POST("SaveTime.php") suspend fun sendTimeData(@FieldMap map: ArrayMap<String, String>): DefaultG
    @FormUrlEncoded @POST("SaveMember.php") suspend fun sendMemberData(@FieldMap map: ArrayMap<String, String>): CreateMemberG
    @FormUrlEncoded @POST("ChildConnect.php") suspend fun sendChildData(@FieldMap map: ArrayMap<String, String>): DefaultG
    @FormUrlEncoded @POST("DelCashData.php") suspend fun sendDelCashData(@FieldMap map: ArrayMap<String, String>): DefaultG
    @FormUrlEncoded @POST("DelTimeData.php") suspend fun sendDelTimeData(@FieldMap map: ArrayMap<String, String>): DefaultG
}