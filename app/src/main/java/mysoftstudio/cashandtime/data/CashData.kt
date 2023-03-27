package mysoftstudio.cashandtime.data

import android.os.Parcel
import android.os.Parcelable

class CashData() : Parcelable {
    var cashId = ""
    var cashDate = ""
    var childId = ""
    var childName = ""
    var cashIn = ""
    var cashOut = ""
    var cashTotal = ""
    var cashCreator = ""
    var cashInfo = ""

    constructor(parcel: Parcel) : this() {
        cashId = parcel.readString().toString()
        cashDate = parcel.readString().toString()
        childId = parcel.readString().toString()
        childName = parcel.readString().toString()
        cashIn = parcel.readString().toString()
        cashOut = parcel.readString().toString()
        cashTotal = parcel.readString().toString()
        cashCreator = parcel.readString().toString()
        cashInfo = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cashId)
        parcel.writeString(cashDate)
        parcel.writeString(childId)
        parcel.writeString(childName)
        parcel.writeString(cashIn)
        parcel.writeString(cashOut)
        parcel.writeString(cashTotal)
        parcel.writeString(cashCreator)
        parcel.writeString(cashInfo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CashData> {
        override fun createFromParcel(parcel: Parcel): CashData {
            return CashData(parcel)
        }

        override fun newArray(size: Int): Array<CashData?> {
            return arrayOfNulls(size)
        }
    }
}